<?xml version="1.0"?>
<globals>
    <global id="manifestOut" value="${manifestDir}" />
    <global id="srcOut" value="${srcDir}/${slashedPackageName(packageName)}" />
    <global id="resOut" value="${resDir}" />
    <global id="relativePackage" value="<#if relativePackage?has_content>${relativePackage}<#else>${packageName}</#if>" />
	<global id="presenterName" value="<#if activityClass?has_content>${activityClass?substring(0,activityClass?index_of('Fragment'))}Presenter<#else>Presenter</#if>" />
	<global id="contractName" value="<#if activityClass?has_content>${activityClass?substring(0,activityClass?index_of('Fragment'))}Contract<#else>Contract</#if>" />
	<global id="modelName" value="<#if activityClass?has_content>${activityClass?substring(0,activityClass?index_of('Fragment'))}Model<#else>Model</#if>" />
</globals>
