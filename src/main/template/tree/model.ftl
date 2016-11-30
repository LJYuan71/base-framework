<#import "function.ftl" as func>
package com.${system}.${modular}.${packagename}.model;

import java.io.Serializable;

public class ${modelname} implements Serializable {

<#list model.columnList as col>
	// ${col.comment}
	<#if (col.colType=="Integer")>
	private Long  ${func.convertUnderLine(col.columnName)};
	<#else>
	private ${col.colType}  ${func.convertUnderLine(col.columnName)};
	</#if>
</#list>
	// 不存数据库  父节点名称
	private String parentname;
	
<#if subtables?exists && subtables?size!=0>
	<#list subtables as table>
	<#assign vars=table.variables>
	//${table.tabComment}列表
	protected List<${vars.class}> ${vars.classVar}List=new ArrayList<${vars.class}>();
	</#list>
	</#if>
<#list model.columnList as col>
	<#assign colName=func.convertUnderLine(col.columnName)>
	public void set${colName?cap_first}(<#if (col.colType="Integer")>Long<#else>${col.colType}</#if> ${colName}) 
	{
		this.${colName} = ${colName};
	}
	/**
	 * 返回 ${col.comment}
	 * @return
	 */
	public <#if (col.colType="Integer")>Long<#else>${col.colType}</#if> get${colName?cap_first}() 
	{
		return this.${colName};
	}
</#list>
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
<#if subtables?exists && subtables?size!=0>
<#list subtables as table>
<#assign vars=table.variables>
	public void set${vars.classVar?cap_first}List(List<${vars.class}> ${vars.classVar}List) 
	{
		this.${vars.classVar}List = ${vars.classVar}List;
	}
	/**
	 * 返回 ${table.tabComment}列表
	 * @return
	 */
	public List<${vars.class}> get${vars.classVar?cap_first}List() 
	{
		return this.${vars.classVar}List;
	}
</#list>
</#if>
}