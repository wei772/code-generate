package cn.garden.generate.entity;

import cn.garden.generate.util.ExceptionUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.*;

/**
 * 实体类型
 * 主要处理各种类型转换
 */
public class EntityType {

    private static final Map<EntityTypeValue, JDBCType> VALUE_TO_JDBC_TYPE_MAP = new HashMap<>();
    private static final Map<JDBCType, EntityTypeValue> JDBC_TYPE_TO_VALUE_MAP = new HashMap<>();
    private static final Map<LanguageType, String> LANGUAGE_IMPORT_GRAMMAR_MAP = new HashMap<>();
    private static final List<EntityTypeLanguageConfig> ENTITY_TYPE_LANGUAGE_CONFIGS = new ArrayList<>();

    static {
        initEntityTypeMap();
        initLanguageImportGrammarMap();
    }

    private final EntityTypeValue value;
    private final JDBCType jdbcType;
    private LanguageType targetLanguage;

    public EntityType(String value) {
        this.value = EntityTypeValue.of(value);
        this.jdbcType = convertToJdbcType(this.value);
    }

    public EntityType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
        this.value = convertToType(jdbcType);
    }


    private static void initLanguageImportGrammarMap() {
        LANGUAGE_IMPORT_GRAMMAR_MAP.put(LanguageType.JAVA, "import %s;");
    }

    private static void initEntityTypeMap() {
        addDoubleJdbcToValueMap(EntityTypeValue.STRING, JDBCType.VARCHAR);
        addJdbcToValueMap(
                Arrays.asList(
                        JDBCType.CHAR
                        , JDBCType.CLOB
                        , JDBCType.NCLOB
                        , JDBCType.NVARCHAR
                        , JDBCType.NCHAR
                        , JDBCType.LONGNVARCHAR
                        , JDBCType.LONGVARCHAR
                ),
                EntityTypeValue.STRING);
        addLanguageConfig(EntityTypeValue.STRING, true, "java.lang.String");

        addDoubleJdbcToValueMap(EntityTypeValue.LONG, JDBCType.BIGINT);
        addLanguageConfig(EntityTypeValue.LONG, true, "java.lang.Long");

        addDoubleJdbcToValueMap(EntityTypeValue.INTEGER, JDBCType.INTEGER);
        addLanguageConfig(EntityTypeValue.INTEGER, true, "java.lang.Integer");

        addDoubleJdbcToValueMap(EntityTypeValue.DOUBLE, JDBCType.DOUBLE);
        addLanguageConfig(EntityTypeValue.DOUBLE, true, "java.lang.Double");

        addDoubleJdbcToValueMap(EntityTypeValue.LOCAL_DATE_TIME, JDBCType.TIMESTAMP);
        addJdbcToValueMap(
                List.of(JDBCType.TIMESTAMP_WITH_TIMEZONE),
                EntityTypeValue.LOCAL_DATE_TIME);
        addLanguageConfig(EntityTypeValue.LOCAL_DATE_TIME, false, "java.time.LocalDateTime");

        addDoubleJdbcToValueMap(EntityTypeValue.LOCAL_DATE, JDBCType.DATE);
        addLanguageConfig(EntityTypeValue.LOCAL_DATE, false, "java.time.LocalDate");

        addDoubleJdbcToValueMap(EntityTypeValue.LOCAL_TIME, JDBCType.TIME);
        addLanguageConfig(EntityTypeValue.LOCAL_TIME, false, "java.time.LocalTime");

        addDoubleJdbcToValueMap(EntityTypeValue.BIG_DECIMAL, JDBCType.DECIMAL);
        addLanguageConfig(EntityTypeValue.BIG_DECIMAL, false, "java.math.BigDecimal");


        addDoubleJdbcToValueMap(EntityTypeValue.BOOLEAN, JDBCType.BOOLEAN);
        addLanguageConfig(EntityTypeValue.BOOLEAN, true, "java.lang.Boolean");
    }

    private static void addLanguageConfig(EntityTypeValue string, Boolean builtIn, String languageType) {
        ENTITY_TYPE_LANGUAGE_CONFIGS.add(new EntityTypeLanguageConfig(
                string, LanguageType.JAVA
                , builtIn, languageType));
    }


    private static void addJdbcToValueMap(List<JDBCType> jdbcTypes, EntityTypeValue value) {
        for (JDBCType jdbcType : jdbcTypes) {
            JDBC_TYPE_TO_VALUE_MAP.put(jdbcType, value);
        }
    }

    private static void addDoubleJdbcToValueMap(EntityTypeValue value, JDBCType jdbcType) {
        VALUE_TO_JDBC_TYPE_MAP.put(value, jdbcType);
        JDBC_TYPE_TO_VALUE_MAP.put(jdbcType, value);
    }

    public String getValue() {
        return value.getName();
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    private EntityTypeValue convertToType(JDBCType jdbcType) {
        EntityTypeValue valueEnum = JDBC_TYPE_TO_VALUE_MAP.get(jdbcType);
        if (Objects.isNull(valueEnum)) {
            throw ExceptionUtil.createDefaultException("未找到JdbcType:" + jdbcType.getName() + "对应的类型");
        }
        return valueEnum;
    }

    private JDBCType convertToJdbcType(EntityTypeValue value) {
        JDBCType type = VALUE_TO_JDBC_TYPE_MAP.get(value);
        if (Objects.isNull(type)) {
            throw ExceptionUtil.createDefaultException("未找到Value:" + value.getName() + "对应的Jdbc类型");
        }
        return type;
    }

    public LanguageType getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageType targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getImportString() {
        EntityTypeLanguageConfig targetLanguageType = getTargetLanguageType(value);
        if (BooleanUtils.isTrue(targetLanguageType.builtIn())) {
            return StringUtils.EMPTY;
        }
        return getImportGrammar(targetLanguageType.language(), targetLanguageType.languageType());
    }

    private EntityTypeLanguageConfig getTargetLanguageType(EntityTypeValue value) {
        return getTargetLanguageType(value, targetLanguage);
    }

    private EntityTypeLanguageConfig getTargetLanguageType(EntityTypeValue value, LanguageType targetLanguage) {
        EntityTypeLanguageConfig entityTypeLanguageConfig = ENTITY_TYPE_LANGUAGE_CONFIGS.stream()
                .filter(
                        m -> Objects.equals(m.language(), targetLanguage)
                                && Objects.equals(m.value(), value)
                )
                .findFirst()
                .orElse(null);

        if (Objects.isNull(entityTypeLanguageConfig)) {
            throw ExceptionUtil.createDefaultException("找不到对应的类型配置" + value + targetLanguage);
        }
        return entityTypeLanguageConfig;
    }

    private String getImportGrammar(LanguageType targetLanguage, String type) {
        String format = LANGUAGE_IMPORT_GRAMMAR_MAP.get(targetLanguage);
        return String.format(format, type);
    }

    public record EntityTypeLanguageConfig(
            EntityTypeValue value
            , LanguageType language
            , Boolean builtIn
            , String languageType) {
    }
}
