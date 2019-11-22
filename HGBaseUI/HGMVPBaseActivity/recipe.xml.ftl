<?xml version="1.0"?>
<recipe>
    <merge from="AndroidManifest.xml.ftl"
            to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    <merge from="res/values/strings.xml.ftl"
            to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

    <merge from="res/layout/activity_simple.xml.ftl"
            to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />

    <instantiate from="src/app_package/SimpleActivity.java.ftl"
            to="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
				   
	<instantiate from="src/app_package/SimpleContract.java.ftl"
            to="${escapeXmlAttribute(srcOut)}/Contract/${contractName}.java" />
				   
	<instantiate from="src/app_package/SimpleModel.java.ftl"
            to="${escapeXmlAttribute(srcOut)}/Model/${modelName}.java" />
				   
	<instantiate from="src/app_package/SimplePresenter.java.ftl"
            to="${escapeXmlAttribute(srcOut)}/Presenter/${presenterName}.java" />

    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
	<open file="${escapeXmlAttribute(srcOut)}/Contract/${contractName}.java" />
	<open file="${escapeXmlAttribute(srcOut)}/Model/${modelName}.java" />
	<open file="${escapeXmlAttribute(srcOut)}/Presenter/${presenterName}.java" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</recipe>
