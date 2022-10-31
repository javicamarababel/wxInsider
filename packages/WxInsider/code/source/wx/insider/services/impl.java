package wx.insider.services;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.Namespace;
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSService;
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSRecordRef;
import com.wm.lang.schema.ContentType;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSSignature;
import com.wm.lang.ns.NSInterface;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowMapCopy;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapDelete;
import com.wm.lang.flow.FlowMapSet;
import com.wm.lang.flow.MapWmPathInfo;
import com.wm.lang.flow.FlowInvoke;
import com.wm.lang.flow.FlowSequence;
import com.wm.lang.flow.FlowBranch;
import com.wm.lang.flow.FlowExit;
// --- <<IS-END-IMPORTS>> ---

public final class impl

{
	// ---( internal utility methods )---

	final static impl _instance = new impl();

	static impl _newInstance() { return new impl(); }

	static impl _cast(Object o) { return (impl)o; }

	// ---( server methods )---




	public static final void dumpService (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dumpService)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fullServiceName
		// [o] field:0:required dumpInput
		// [o] field:0:required dumpOutput
		// [o] field:0:required dumpImpl
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	fullServiceName = IDataUtil.getString( pipelineCursor, "fullServiceName" );
		pipelineCursor.destroy();
		try {
			NSName svcNS=NSName.create(fullServiceName);
			NSNode svcNode=com.wm.app.b2b.server.ns.Namespace.current().getNode(svcNS);
			if (svcNode==null)
				throw new NullPointerException("Servicio "+fullServiceName+" no encontrado");
			if (!(svcNode instanceof FlowSvcImpl))
				throw new NullPointerException(fullServiceName+" no es un servicio Flow (class="+svcNode.getClass().getName());
			FlowSvcImpl svc=(FlowSvcImpl)svcNode;
			
			DumpFormat fmt=new DumpFormatHTML();
			FlowDumper fd=new FlowDumper(fmt);
			StringBuilder dumpInput=new StringBuilder()
					, dumpOutput=new StringBuilder()
					, dumpImpl=new StringBuilder();
			NSSignature sig=svc.getSig();
			dumpInput.append(fd.dump(sig.getInput()));
			dumpOutput.append(fd.dump(sig.getOutput()));
			FlowRoot fw=svc.getFlowRoot();
			dumpImpl.append(fd.dump(fw));
		
			// pipeline
			IDataCursor pipelineCursor_1 = pipeline.getCursor();
			IDataUtil.put( pipelineCursor_1, "dumpInput", dumpInput.toString() );
			IDataUtil.put( pipelineCursor_1, "dumpOutput", dumpOutput.toString() );
			IDataUtil.put( pipelineCursor_1, "dumpImpl", dumpImpl.toString() );
			pipelineCursor_1.destroy();
		}
		catch (Exception e) {
			ServiceException se=new ServiceException("Error volcando service \""+fullServiceName+"\"");
			se.initCause(e);
			throw se;
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void dumpServices (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dumpServices)>> ---
		// @sigtype java 3.5
		// [i] field:0:required namespace
		// [o] field:0:required dump
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	namespaceToDump = IDataUtil.getString( pipelineCursor, "namespace" );
		pipelineCursor.destroy();
		try {
			Namespace namespace=com.wm.app.b2b.server.ns.Namespace.current();
			NSNode nodeToDump=namespace.getNode(NSName.create(namespaceToDump));
			if (nodeToDump==null)
				throw new ServiceException("Namespace no encontrado \""+namespaceToDump+"\"");
			DumpFormat fmt=new DumpFormatHTML();
			ServiceDumper sd=new ServiceDumper(fmt);
			String dump=sd.dump(nodeToDump);
			pipelineCursor = pipeline.getCursor();
				IDataUtil.put( pipelineCursor, "dump",dump );
			pipelineCursor.destroy();
		}
		catch (ServiceException e) {
			throw e;
		}
		catch (Exception e) {
			ServiceException se=new ServiceException("Error volcando servicios de namespace \""+namespaceToDump+"\"");
			se.initCause(e);
			throw se;
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateDocMap (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateDocMap)>> ---
		// @sigtype java 3.5
		// [i] field:0:required existingMapServiceName
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	existingMapServiceName = IDataUtil.getString( pipelineCursor, "existingMapServiceName" );
		pipelineCursor.destroy();
		try {
			NSName svcNS=NSName.create(existingMapServiceName);
			NSNode svcNode=com.wm.app.b2b.server.ns.Namespace.current().getNode(svcNS);
			if (svcNode==null)
				throw new NullPointerException("Servicio "+existingMapServiceName+" no encontrado");
			if (!(svcNode instanceof FlowSvcImpl))
				throw new NullPointerException(existingMapServiceName+" no es un servicio Flow (class="+svcNode.getClass().getName());
			FlowSvcImpl svc=(FlowSvcImpl)svcNode;
			
			NSSignature sig=svc.getSig();
			NSRecord input=sig.getInput();
			NSRecord output=sig.getOutput();
			DocMapGen dmg=new DocMapGen();
			FlowElement map=dmg.genMap(input,output);
			
			DumpFormatText fmt=new DumpFormatText();
			FlowDumper fd=new FlowDumper(fmt);
			String dump=fd.dump(map);
		
			// pipeline
			IDataCursor pipelineCursor_1 = pipeline.getCursor();
			IDataUtil.put( pipelineCursor_1, "dump", dump );
			pipelineCursor_1.destroy();
		}
		catch (Exception e) {
			ServiceException se=new ServiceException("Error volcando service \""+existingMapServiceName+"\"");
			se.initCause(e);
			throw se;
		}
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static final int MAX_REC_DEPTH=10;
	
	static class ServiceDumper {
		DumpFormat fmt;
		ServiceDumper (DumpFormat fmt) {
			this.fmt=fmt;
		}
		String dump(NSNode node) {
	log("dump node "+node.getNSName().toString());
			StringBuilder dump=new StringBuilder();
			if (node instanceof NSInterface) {
				NSInterface folder=(NSInterface)node;
				dumpFolderHeader(dump,folder);
				NSNode[] nodes=folder.getNodes();
				if (nodes!=null) {
					for (int i=0;i<nodes.length;++i) {
						dump.append(dump(nodes[i]));
					}
				}
			}
			else if (node instanceof FlowSvcImpl) {
				dump.append(dump((FlowSvcImpl)node));
			}
			return dump.toString();
		}
		void dumpFolderHeader(StringBuilder dump,NSInterface node) {
			dump.append(fmt.genFolderStart());
			dump.append("Carpeta ");
			dump.append(node.getNSName().toString());
			dump.append(fmt.genFolderEnd());
			fmt.insertIndent(dump, true, 0);
		}
		void dumpServiceHeader(StringBuilder dump,FlowSvcImpl svc) {
			dump.append(fmt.genServiceStart());
			dump.append("Servicio ");
			dump.append(svc.getNSName().toString());
			dump.append(fmt.genServiceEnd());
			fmt.insertIndent(dump, true, 0);
		}
		void dumpServiceSubHeader(StringBuilder dump,String header) {
			dump.append(fmt.genServiceSubHeaderStart());
			dump.append(header);
			dump.append(fmt.genServiceSubHeaderEnd());
			fmt.insertIndent(dump, true, 0);
		}
		String dump(FlowSvcImpl svc) {
	log("dump flowsvc "+svc.getNSName().toString());
			StringBuilder dump=new StringBuilder();
			dumpServiceHeader(dump,svc);
	
			FlowDumper fd=new FlowDumper(fmt);
			
			NSSignature sig=svc.getSig();
			
			dumpServiceSubHeader(dump,"Input");
			dump.append(fd.dump(sig.getInput()));
			
			dumpServiceSubHeader(dump,"Output");
			dump.append(fd.dump(sig.getOutput()));
			
			FlowRoot fw=svc.getFlowRoot();
			dumpServiceSubHeader(dump,"Implementation");
			dump.append(fd.dump(fw));
			
			return dump.toString();
		}
	} // class ServiceDumper 
	
	static class FlowDumper {
		int indent=0;
		DumpFormat fmt;
		
		FlowDumper(DumpFormat fmt) {
			this.fmt=fmt;
		}
		
		String dump(FlowElement fe) {
			return dump(fe,false);
		}
		String getLabel(FlowElement fe) {
			String label=fe.getName();
			if (label!=null) {
				if (fe instanceof FlowMapSet && "Setter".equals(label))
					label=null;
			}
			return label;
		}
		String dump(FlowElement fe, boolean omitFirstIndent) {
			StringBuilder dump=new StringBuilder();
			if (fe instanceof FlowRoot) {
				dumpChildren(dump,fe,false);
			}
			else {
				if (!omitFirstIndent)
					insertIndent(dump);
				if (!fe.isEnabled()) {
					dump.append(fmt.genDisabledStart());
					dump.append("/* disabled ");
				}
				String label=getLabel(fe);
				if (label!=null) {
					dump.append(label);
					dump.append(": ");
				}
				
				if (fe instanceof FlowMapCopy)
					dump.append(dump((FlowMapCopy)fe));
				else if (fe instanceof FlowMapDelete)
					dump.append(dump((FlowMapDelete)fe));
				else if (fe instanceof FlowMapInvoke)
					dump.append(dump((FlowMapInvoke)fe));
				else if (fe instanceof FlowMap)
					dump.append(dump((FlowMap)fe));
				else if (fe instanceof FlowMapSet)
					dump.append(dump((FlowMapSet)fe));
				else if (fe instanceof FlowInvoke)
					dump.append(dump((FlowInvoke)fe));
				else if (fe instanceof FlowSequence)
					dump.append(dump((FlowSequence)fe));
				else if (fe instanceof FlowBranch)
					dump.append(dump((FlowBranch)fe));
				else if (fe instanceof FlowExit)
					dump.append(dump((FlowExit)fe));
				else {
					defaultDump(dump,fe);
				}
				if (!fe.isEnabled()) {
					dump.append("*/");
					dump.append(fmt.genDisabledEnd());
				}
			}
			return dump.toString();
		}
		void defaultDump(StringBuilder dump, FlowElement fe) {
			defaultDump(dump,fe,true);
		}
		void appendCmt(StringBuilder dump,FlowElement fe) {
			String cmt=fe.getComment();
			if (cmt!=null && cmt.length()>0) {
				dump.append(fmt.genComentario(cmt));
			}
		}
		void dumpStart(StringBuilder dump, FlowElement fe, boolean includeClassName) {
			String first=fe.toString();
			dump.append(first);
			if (includeClassName) {
				dump.append(" (");
				dump.append(fe.getClass().getName());
				dump.append(")");
			}
			appendCmt(dump,fe);
			//dump.append(" {");
			
		}
		void defaultDump(StringBuilder dump, FlowElement fe, boolean includeClassName) {
			dumpStart(dump,fe,includeClassName);
			dumpChildren(dump,fe);
			dumpEnd(dump,fe);
		}
		void dumpEnd(StringBuilder dump,FlowElement fe) {
			/*insertIndent(dump,true);
			String first=fe.toString();
			dump.append("} ");
			dump.append(fmt.genComentario(first));*/
		}
		void dumpChildren(StringBuilder dump, FlowElement fe) {
			dumpChildren(dump, fe, true);
		}
		void dumpChildren(StringBuilder dump, FlowElement fe,boolean doIndent) {
			FlowElement[] children=fe.getNodes();
			if (children!=null) {
				if (doIndent)
					++indent;
				for (FlowElement cfe:children) {
					appendEol(dump);
					String childDump=dump(cfe);
					dump.append(childDump);
				}
				if (doIndent)
					--indent;
			}
		}
		
		StringBuilder insertIndent(StringBuilder dump) {
			return insertIndent(dump,false);
		}
		StringBuilder insertIndent(StringBuilder dump,boolean eol) {
			return fmt.insertIndent(dump, eol, indent);
		}
		
		StringBuilder appendEol(StringBuilder dump) {
			return fmt.appendEol(dump);
		}
	
		String dump(FlowMapCopy fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			dump.append(dumpMapSource(fe.getParsedFrom()));
			dump.append(dumpMapTarget(fe.getParsedTo()));
			
			return dump.toString();
		}
		
		String dump(FlowMapDelete fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			dump.append(dumpMapSource(fe.getParsedPath()));
			
			return dump.toString();
		}
		
		String dump(FlowMapInvoke fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			dump.append(fe.getService().toString());
			
			dumpIOMap(dump,"Input",fe.getInputMap()); 
			dumpIOMap(dump,"Output",fe.getOutputMap()); 
			
			return dump.toString();
		}
		
		void dumpIOMap(StringBuilder dump,String inputOutput,FlowMap fe) { 
			if (fe!=null && fe.getNodeCount()>0) {
				++indent;
				insertIndent(dump,true);
				dump.append(inputOutput);
				dump.append(" ");
				dump.append(dump(fe, true));
				--indent;
			}
		}
	
		String dump(String inout,NSRecord rec) {
			StringBuilder dump=new StringBuilder();
			if (inout!=null) {
				dump.append(inout);
				dump.append(":");
				appendEol(dump);
			}
			dump.append(dump(rec));
			return dump.toString();
		}
		String dump(NSRecord rec) {
			return dump(rec,MAX_REC_DEPTH);
		}
		String dump(NSRecord rec, int depthLeft) {
			if (depthLeft<=0) {
				return "(Huy - recursivo, ej. getLastError)";
			}
			StringBuilder dump=new StringBuilder();
			boolean firstField=true;
			int depthLeft1=depthLeft-1;
			for (NSField field:rec.getActualFields()) {
				insertIndent(dump,!firstField);
				if (firstField)
					firstField=false;
				dump.append(field.getName());
				if (field instanceof NSRecord) {
					++indent;
					dump.append(dump((NSRecord)field,depthLeft1));
					--indent;
				}
			}
			return dump.toString();
		}
		
		String dump(FlowMap fe) {
			StringBuilder dump=new StringBuilder();
			defaultDump(dump,fe,false);
			return dump.toString();
		}
		String dump(FlowMapSet fe) {
			StringBuilder dump=new StringBuilder();
			dump.append(fe.toString());
			dump.append(" ");
			dump.append(dumpValue(fe.getInput()));
			dump.append(dumpMapTarget(fe.getParsedPath()));
	
			return dump.toString();
		}
		String dumpValue(Object value) {
			StringBuilder dump=new StringBuilder();
			if (value==null)
				dump.append("(null value)");
			else if (value instanceof IData) {
				IData v=(IData)value;
				dump.append("{");
				dump.append(fmt.genComentario("valor de documento:"));
				++indent;
				IDataCursor c=v.getCursor();
				boolean more;
				for (more=c.first();more;more=c.next()) {
					insertIndent(dump,true);
					dump.append(c.getKey());
					dump.append("=");
					dump.append(dumpValue(c.getValue()));
				}
				--indent;
				insertIndent(dump,true);
				dump.append("}");
			}
			else if (value instanceof Object[]) {
				Object[] v=(Object[])value;
				dump.append("{");
				dump.append(fmt.genComentario("array:"));
				++indent;
				for (int i=0;i<v.length;++i) {
					insertIndent(dump,true);
					dump.append(dumpValue(v[i]));
				}
				--indent;
				insertIndent(dump,true);
				dump.append("}");
			}
			else {
				dump.append(fmt.genLiteral("'"+value+"'"));
			}
			return dump.toString();
		}
		String dumpMapSource(String name) {
			StringBuilder dump=new StringBuilder();
			dump.append(name);
			return dump.toString();
		}
		String dumpMapSource(MapWmPathInfo path) {
			return dumpMapSource(path.getPathDisplayString());
		}
		
		String dumpMapTarget(MapWmPathInfo path) {
			StringBuilder dump=new StringBuilder();
			dump.append(" -> ");
			dump.append(path.getPathDisplayString());
			return dump.toString();
		}
		
		String dump(FlowInvoke fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			dump.append(fe.getService().toString());
			appendCmt(dump,fe);
			//dump.append(" {");
			
			dumpIOMap(dump,"Input",fe.getInputMap()); 
			dumpIOMap(dump,"Output",fe.getOutputMap()); 
			
			dumpEnd(dump,fe);
			return dump.toString();
		}
		
		String dump(FlowSequence fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			String ft=fe.getFormType();
			if (ft!=null && !"SEQUENCE".equals(ft)) {
				dump.append("(");
				dump.append(ft);
				dump.append(")");
			}
			dump.append(" ");
			int exitOn=fe.getExitOn();
			if (exitOn!=0 /* FAILURE */) {
				dump.append(" exit on ");
				String exitOnStr;
				if (exitOn==1)
					exitOnStr="Success";
				else if (exitOn==2)
					exitOnStr="Done";
				else
					exitOnStr="("+exitOn+")";
				dump.append(exitOnStr);
			}
			appendCmt(dump,fe);
			dumpChildren(dump,fe);
			dumpEnd(dump,fe);
			return dump.toString();
		}
		
		String dump(FlowBranch fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			String switchOn=fe.getBranchSwitch();
			if (switchOn!=null && switchOn.length()>0) {
				dump.append("on ");
				dump.append(switchOn);
			}
			appendCmt(dump,fe);
			dumpChildren(dump,fe);
			dumpEnd(dump,fe);
			return dump.toString();
		}
	
		String dump(FlowExit fe) {
			StringBuilder dump=new StringBuilder();
			
			dump.append(fe.toString());
			dump.append(" ");
			String exitFrom=fe.getExitFrom();
			dump.append(" from ");
			dump.append(exitFrom);
			String exitWith=fe.getSignal();
			dump.append(" with ");
			dump.append(exitWith);
			String exitMsg=fe.getFailureMessage();
			if (exitMsg!=null && exitMsg.length()>0) {
				dump.append(" ");
				dump.append(dumpValue(exitMsg));
			}
			appendCmt(dump,fe);
			dumpChildren(dump,fe);
			dumpEnd(dump,fe);
			return dump.toString();
		}
	} // class FlowDumper
	
	static abstract class DumpFormat {
		abstract StringBuilder insertIndent(StringBuilder dump,boolean eol,int indent);
		abstract StringBuilder appendEol(StringBuilder dump);
		abstract String genComentario(String cmt);
		abstract String genLiteral(String cmt);
		abstract String genDisabledStart();
		abstract String genDisabledEnd();
		abstract String genFolderStart();
		abstract String genFolderEnd();
		abstract String genServiceStart();
		abstract String genServiceEnd();
		abstract String genServiceSubHeaderStart();
		abstract String genServiceSubHeaderEnd();
	} // class DumpFormat
	
	static class DumpFormatText extends DumpFormat {
		StringBuilder insertIndent(StringBuilder dump,boolean eol,int indent) {
			if (eol)
				dump.append('\n');
			for (int i=0;i<indent;++i) {
				dump.append("  ");
			}
			return dump;
		}
		StringBuilder appendEol(StringBuilder dump) {
			dump.append('\n');
			return dump;
		}
		String genComentario(String cmt) {
			StringBuilder dump=new StringBuilder();
			dump.append(" (");
			dump.append(cmt);
			dump.append(")");
			return dump.toString();
		}
		String genLiteral(String cmt) {
			return cmt;
		}
		String genDisabledStart() {
			return "";
		}
		String genDisabledEnd() {
			return "";
		}
		String genFolderStart() {
			return "-- ";
		}
		String genFolderEnd() {
			return "";
		}
		String genServiceStart() {
			return "---- ";
		}
		String genServiceEnd() {
			return "";
		}
		String genServiceSubHeaderStart() {
			return "";
		}
		String genServiceSubHeaderEnd() {
			return "";
		}
	} // class DumpFormatText 
	
	static class DumpFormatHTML extends DumpFormat {
		String escape(String tx) {
			StringBuilder etx=new StringBuilder();
			int i,I;
			for (i=0,I=tx.length();i<I;++i) {
				char c=tx.charAt(i);
				if (c=='<')
					etx.append("&lt;");
				else if (c=='>')
					etx.append("&gt;");
				else if (c=='&')
					etx.append("&amp;");
				else
					etx.append(c);
			}
			return etx.toString();
		}
		StringBuilder insertIndent(StringBuilder dump,boolean eol,int indent) {
			if (eol)
				dump.append("<br/>");
			for (int i=0;i<indent;++i) {
				dump.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			return dump;
		}
		StringBuilder appendEol(StringBuilder dump) {
			dump.append("<br/>");
			return dump;
		}
		String genComentario(String cmt) {
			StringBuilder dump=new StringBuilder();
			dump.append(" /* <i class='comentario'>");
			dump.append(escape(cmt));
			dump.append("</i> */");
			return dump.toString();
		}
		String genLiteral(String cmt) {
			StringBuilder dump=new StringBuilder();
			dump.append("<code>");
			dump.append(escape(cmt));
			dump.append("</code>");
			return dump.toString();
		}
		String genDisabledStart() {
			return "<span class='disabled'>";
		}
		String genDisabledEnd() {
			return "</span>";
		}
		String genFolderStart() {
			return "<h2>";
		}
		String genFolderEnd() {
			return "</h2>";
		}
		String genServiceStart() {
			return "<h3>";
		}
		String genServiceEnd() {
			return "</h3>";
		}
		String genServiceSubHeaderStart() {
			return "<h4>";
		}
		String genServiceSubHeaderEnd() {
			return "</h4>";
		}
	} // class DumpFormatHTML
	
	static class DocMapGen {
		
		boolean algunMapNoTrivial;
		
		FlowElement genMap(NSRecord input, NSRecord output) {
			return genMap(null,null,input,null,output);
		}
		FlowElement genMap(FlowMap map,String inputParent, NSRecord input, String outputParent, NSRecord output) {
			/* Generaremos un map por cada field */
			if (map==null) {
				map=createFlowMap();
			}
			algunMapNoTrivial=false;
			for (NSField field:input.getActualFields()) {
				String fieldName=field.getName();
				if (inputParent!=null)
					fieldName=inputParent+"/"+fieldName;
				if (!(field instanceof NSRecord)) {
					FlowMapCopy mc=createFlowMapCopy();
					mc.setMapFrom(fieldName);
					mc.setMapTo("TODO "+fieldName);
	//	System.out.println("Antes de a\u00F1adir mapCopy , map="+map.hashCode()+", map.children#="+map.getNodeCount());
					map.addNode(mc);
	//	System.out.println("A\u00F1adido mapCopy "+new FlowDumper().dump(mc)+", map="+map.hashCode()+", map.children#="+map.getNodeCount());
				}
				else
					genMap(map,fieldName,(NSRecord)field,"TODO",null);
			}
			return map;
		}
			
		FlowMap createFlowMap() {
			return new FlowMap(null);
		}
		FlowMapCopy createFlowMapCopy() {
			return new FlowMapCopy(null);
		}
		
	} // class DocMapGen
	
	static void log(String msg) {
	
		try {
			// input
			IData input = IDataFactory.create();
			IDataCursor inputCursor = input.getCursor();
			IDataUtil.put( inputCursor, "message", msg);
			IDataUtil.put( inputCursor, "function", "dumpServices" );
			IDataUtil.put( inputCursor, "level", "INFO" );
			inputCursor.destroy();
	
			Service.doInvoke( "pub.flow", "debugLog", input );
		}
		catch( Exception e){
			e.printStackTrace();
		}
		
	}
	
		
	// --- <<IS-END-SHARED>> ---
}

