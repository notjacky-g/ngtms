package com.hwacom.ngtms.common.am.rebind;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.hwacom.ngtms.common.am.view.RIPViewer;
import java.io.PrintWriter;

public class RIPViewerInitializerGenerator extends Generator {

  private final String generatedTypeSuffix = "Impl";

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    try {
      TypeOracle typeOracle = context.getTypeOracle();
      JClassType ripViewerInitializerType = typeOracle.getType(typeName);
      logger.log(
          TreeLogger.INFO,
          "Generating source for " + ripViewerInitializerType.getQualifiedSourceName());
      String generatedTypeFullName =
          ripViewerInitializerType.getQualifiedSourceName() + generatedTypeSuffix;
      SourceWriter sourceWriter = getSourceWriter(logger, context, ripViewerInitializerType);
      if (sourceWriter == null) {
        return generatedTypeFullName;
      }

      JClassType ripViewerType = typeOracle.getType(RIPViewer.class.getName());
      String parameterName = "className";
      sourceWriter.println(
          "public "
              + ripViewerType.getQualifiedSourceName()
              + " init(String "
              + parameterName
              + ") {");

      int counter = 0;
      for (JClassType type : typeOracle.getTypes()) {
        if (!type.isAbstract()
            && type.isInterface() == null
            && type.isAssignableTo(ripViewerType)) {
          if (counter == 0) {
            sourceWriter.print("if (\"");
          } else {
            sourceWriter.print("else if (\"");
          }
          sourceWriter.println(
              type.getQualifiedSourceName() + "\".equals(" + parameterName + ")) {");
          sourceWriter.println(
              ripViewerType.getQualifiedSourceName()
                  + " ripViewer = new "
                  + type.getQualifiedSourceName()
                  + "();");
          sourceWriter.println("return ripViewer;");
          sourceWriter.println("}");
          counter++;
        }
      }
      sourceWriter.println("return null;");
      sourceWriter.println("}");
      sourceWriter.commit(logger);
      logger.log(
          TreeLogger.INFO,
          "Done Generating source for " + ripViewerInitializerType.getQualifiedSourceName());

      return generatedTypeFullName;
    } catch (NotFoundException ex) {
      logger.log(Type.ERROR, "Class '" + RIPViewer.class.getName() + "' Not Found", ex);
      throw new UnableToCompleteException();
    }
  }

  private SourceWriter getSourceWriter(
      TreeLogger logger, GeneratorContext context, JClassType classType) {
    String packageName = classType.getPackage().getName();
    String simpleName = classType.getSimpleSourceName() + generatedTypeSuffix;
    PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
    if (printWriter == null) {
      return null;
    }

    ClassSourceFileComposerFactory composer =
        new ClassSourceFileComposerFactory(packageName, simpleName);
    composer.addImplementedInterface(classType.getQualifiedSourceName());

    return composer.createSourceWriter(context, printWriter);
  }
}
