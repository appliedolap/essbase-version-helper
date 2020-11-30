package com.appliedolap.essbase;

import com.appliedolap.essbase.EssbaseVersion.EssbaseFeature;
import com.essbase.api.datasource.*;

public class ExportOutlineTest extends CubeOperationTest {

	public static void main(String[] args) throws Exception {
		new ExportOutlineTest().run(args);
	}

	public void doOperations(IEssCube cube) throws Exception {

		 if (EssbaseFeature.OUTLINE_XML_EXPORT.isAvailable()) {
			export(cube);
		 }
		 
		 if (EssbaseVersion.supports(EssbaseFeature.OUTLINE_XML_EXPORT)) {
			 export(cube);
		 }
		 
		 if (EssbaseVersion.isAtLeast(EssbaseVersion.V11_1_2)) {
			 export(cube);
		 }
		 
	}
	
	public void export(IEssCube cube) throws Exception {
		EssOtlExportOptions exportOptions = new EssOtlExportOptions();
		exportOptions.setOutputFlag((EssOtlExportOptions.ESS_OTLEXPORT_ALLDIMS));
		byte[] bytes = ((EssCube) cube).exportOutline(exportOptions, null);
		String outlineXML = new String(bytes, "UTF-8");
		System.out.println("Outline XML: " + outlineXML);
	}
}



// package com.appliedolap.dodeca.essbase.server.actions.outline;
//
// import com.appliedolap.dodeca.essbase.server.ActionResults;
// import com.appliedolap.dodeca.essbase.server.IActionImplementation;
// import com.appliedolap.dodeca.essbase.server.actions.EssOperation;
// import com.appliedolap.dodeca.essbase.server.connection.EssConnectionManager;
// import com.appliedolap.dodeca.essbase.server.xml.GenericWriter;
// import com.appliedolap.dodeca.shared.server.sax.utils.Strings;
// import com.essbase.api.base.EssException;
// import com.essbase.api.datasource.*;
// import com.essbase.api.session.IEssbase;
//
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
//
// public class GetOutlineXML extends EssOperation implements IActionImplementation {
// /**
// * Creates a new GetOutlineXML class.
// */
// public GetOutlineXML() {
// super();
// }
//
// @Override
// protected void doOperations(EssConnectionManager essConnectionManager, String action,
// ActionResults results) throws EssException, IOException {
// // get the cube
// IEssCube cube = essConnectionManager.getCube();
//
// // set the options for the maxl xml extract
// EssOtlExportOptions exportOptions = new EssOtlExportOptions();
//
// // set the export type
// exportOptions.setOutputFlag((EssOtlExportOptions.ESS_OTLEXPORT_ALLDIMS));
//
// byte[] bytes = null;
//
// // call the 12.2.1.1 version
// bytes = getOutline1(cube, exportOptions);
//
// // if the 12.2.1.1. version returns null, then try the 11.1.2 version
// if (bytes == null) {
// bytes = getOutline2(cube, exportOptions);
// }
//
// // get the outline string
// String outlineXML = new String(bytes, Strings.UTF8);
//
// // write it to the output stream
// GenericWriter writer = new GenericWriter();
// ByteArrayOutputStream out = results.getOutputStream();
//
// writer.writeElement("OutlineXML", outlineXML, out);
//
// }
//
// private byte[] getOutline1(IEssCube cube, EssOtlExportOptions exportOptions) throws EssException
// {
// byte[] bytes = null;
//
//
// return bytes;
// }
//
// private byte[] getOutline2(IEssCube cube, EssOtlExportOptions exportOptions) throws EssException
// {
// byte[] bytes = null;
//
// // get the outline
// bytes = ((EssCube) cube).exportOutline(exportOptions, null);
//
// return bytes;
// }
//
// @Override
// protected boolean isAdminCredentialsRequired() {
// return true;
// }
//
// }