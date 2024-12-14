package cn.garden.entity;

import cn.garden.entity.enums.EntityTypeValueEnum;
import cn.garden.entity.enums.LanguageEnum;
import cn.garden.util.ExceptionUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.*;

/**
 * 实体类型
 * 主要处理各种类型转换
 */
public class EntityType {

    private static final Map<EntityTypeValueEnum, JDBCType> VALUE_TO_JDBC_TYPE_MAP = new HashMap<>();
    private static final Map<JDBCType, EntityTypeValueEnum> JDBC_TYPE_TO_VALUE_MAP = new HashMap<>();
    private static final Map<LanguageEnum, String> LANGUAGE_IMPORT_GRAMMAR_MAP = new HashMap<>();
    private static final List<EntityTypeLanguageConfig> ENTITY_TYPE_LANGUAGE_CONFIGS = new ArrayList<>();

    static {
        initEntityTypeMap();
        initLanguageImportGrammarMap();
    }

    private final EntityTypeValueEnum value;
    private final JDBCType jdbcType;
    private LanguageEnum targetLanguage;

    public EntityType(String value) {
        this.value = EntityTypeValueEnum.getEnum(value);
        this.jdbcType = convertToJdbcType(this.value);
    }

    public EntityType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
        this.value = convertToType(jdbcType);
    }


    private static void initLanguageImportGrammarMap() {
        LANGUAGE_IMPORT_GRAMMAR_MAP.put(LanguageEnum.JAVA, "import %s;");
    }

    private static void initEntityTypeMap() {
        addDoubleJdbcToValueMap(EntityTypeValueEnum.STRING, JDBCType.VARCHAR);
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
                EntityTypeValueEnum.STRING);
        addLanguageConfig(EntityTypeValueEnum.STRING, true, "java.lang.String");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.LONG, JDBCType.BIGINT);
        addLanguageConfig(EntityTypeValueEnum.LONG, true, "java.lang.Long");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.INTEGER, JDBCType.INTEGER);
        addLanguageConfig(EntityTypeValueEnum.INTEGER, true, "java.lang.Integer");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.DOUBLE, JDBCType.DOUBLE);
        addLanguageConfig(EntityTypeValueEnum.DOUBLE, true, "java.lang.Double");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.LOCAL_DATE_TIME, JDBCType.TIMESTAMP);
        addJdbcToValueMap(
                List.of(JDBCType.TIMESTAMP_WITH_TIMEZONE),
                EntityTypeValueEnum.LOCAL_DATE_TIME);
        addLanguageConfig(EntityTypeValueEnum.LOCAL_DATE_TIME, false, "java.time.LocalDateTime");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.LOCAL_DATE, JDBCType.DATE);
        addLanguageConfig(EntityTypeValueEnum.LOCAL_DATE, false, "java.time.LocalDate");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.LOCAL_TIME, JDBCType.TIME);
        addLanguageConfig(EntityTypeValueEnum.LOCAL_TIME, false, "java.time.LocalTime");

        addDoubleJdbcToValueMap(EntityTypeValueEnum.BIG_DECIMAL, JDBCType.DECIMAL);
        addLanguageConfig(EntityTypeValueEnum.BIG_DECIMAL, false, "java.math.BigDecimal");


        addDoubleJdbcToValueMap(EntityTypeValueEnum.BOOLEAN, JDBCType.BOOLEAN);
        addLanguageConfig(EntityTypeValueEnum.BOOLEAN, true, "java.lang.Boolean");
    }

    private static void addLanguageConfig(EntityTypeValueEnum string, Boolean builtIn, String languageType) {
        ENTITY_TYPE_LANGUAGE_CONFIGS.add(new EntityTypeLanguageConfig(
                string, LanguageEnum.JAVA
                , builtIn, languageType));
    }


    private static void addJdbcToValueMap(List<JDBCType> jdbcTypes, EntityTypeValueEnum value) {
        for (JDBCType jdbcType : jdbcTypes) {
            JDBC_TYPE_TO_VALUE_MAP.put(jdbcType, value);
        }
    }

    private static void addDoubleJdbcToValueMap(EntityTypeValueEnum value, JDBCType jdbcType) {
        VALUE_TO_JDBC_TYPE_MAP.put(value, jdbcType);
        JDBC_TYPE_TO_VALUE_MAP.put(jdbcType, value);
    }

    public String getValue() {
        return value.getName();
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    private EntityTypeValueEnum convertToType(JDBCType jdbcType) {
        EntityTypeValueEnum valueEnum = JDBC_TYPE_TO_VALUE_MAP.get(jdbcType);
        if (Objects.isNull(valueEnum)) {
            throw ExceptionUtil.createDefaultException("未找到JdbcType:" + jdbcType.getName() + "对应的类型");
        }
        return valueEnum;
    }

    private JDBCType convertToJdbcType(EntityTypeValueEnum value) {
        JDBCType type = VALUE_TO_JDBC_TYPE_MAP.get(value);
        if (Objects.isNull(type)) {
            throw ExceptionUtil.createDefaultException("未找到Value:" + value.getName() + "对应的Jdbc类型");
        }
        return type;
    }

    public LanguageEnum getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(LanguageEnum targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getImportString() {
        EntityTypeLanguageConfig targetLanguageType = getTargetLanguageType(value);
        if (BooleanUtils.isTrue(targetLanguageType.builtIn())) {
            return StringUtils.EMPTY;
        }
        return getImportGrammar(targetLanguageType.language(), targetLanguageType.languageType());
    }

    private EntityTypeLanguageConfig getTargetLanguageType(EntityTypeValueEnum value) {
        return getTargetLanguageType(value, targetLanguage);
    }

    private EntityTypeLanguageConfig getTargetLanguageType(EntityTypeValueEnum value, LanguageEnum targetLanguage) {
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

    private String getImportGrammar(LanguageEnum targetLanguage, String type) {
        String format = LANGUAGE_IMPORT_GRAMMAR_MAP.get(targetLanguage);
        return String.format(format, type);
    }

    public record EntityTypeLanguageConfig(
            EntityTypeValueEnum value
            , LanguageEnum language
            , Boolean builtIn
            , String languageType) {
    }
}
