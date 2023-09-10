package com.nntk.sb.config;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;  // 默认不显示table字段注释
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String MAPPER_SUFFIX = "Mapper";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";

    /**
     * 设置用户配置的参数
     * 得到在xml中配置在<commentGenerator>中的参数properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        // 配置文件的addRemarkComments属性
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加 @APiModelProperty注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();       //返回数据库字段的注释内容，没的话则为""
        //根据参数和备注信息判断是否添加swagger注解信息
        // 这里为若数据库字段有注释，则生成对应的 @ApiModelProperty(value = "{注释}") 注解
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            //数据库中特殊字符需要转义，防止注释字段中出现双引号(")这个符号
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
            //给model的字段添加swagger注解
            field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
        }
    }


    /**
     * 在生成的每个文件添加swagger的包, import ...，如import io.swagger.annotations.ApiModelProperty;
     *
     * @param compilationUnit the compilation unit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        // 只在model中的非Example添加swagger注解类的导入
        // 这是防止上述添加了@ApiModelProperty注解后却没有导入包的情况
        if (!compilationUnit.getType().getFullyQualifiedName().contains(MAPPER_SUFFIX) &&
                !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
    }
}
