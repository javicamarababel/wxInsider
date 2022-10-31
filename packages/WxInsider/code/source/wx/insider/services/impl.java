package wx.insider.services;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.NSNode;
import com.wm.lang.ns.NSName;
import com.wm.lang.ns.NSService;
import com.wm.app.b2b.server.FlowSvcImpl;
import com.wm.lang.ns.NSRecord;
import com.wm.lang.ns.NSRecordRef;
import com.wm.lang.schema.ContentType;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSSignature;
import com.wm.lang.flow.FlowRoot;
import com.wm.lang.flow.FlowElement;
import com.wm.lang.flow.FlowMapCopy;
import com.wm.lang.flow.FlowMapInvoke;
import com.wm.lang.flow.FlowMap;
import com.wm.lang.flow.FlowMapSet;
import com.wm.lang.flow.MapWmPathInfo;
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
			dumpImpl.append(fd.dump(fw.getFlowRoot()));
		
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
				String label=getLabel(fe);
				if (label!=null) {
					dump.append(label);
					dump.append(": ");
				}
				
				if (fe instanceof FlowMapCopy)
					dump.append(dump((FlowMapCopy)fe));
				else if (fe instanceof FlowMapInvoke)
					dump.append(dump((FlowMapInvoke)fe));
		//			else if (fe instanceof FlowMap)
		//				dump.append(dump((FlowMap)fe));
				else if (fe instanceof FlowMapSet)
					dump.append(dump((FlowMapSet)fe));
				else {
					defaultDump(dump,fe);
				}
			}
			return dump.toString();
		}
		void defaultDump(StringBuilder dump, FlowElement fe) {
			dump.append(fe.toString());
			dump.append(" (");
			dump.append(fe.getClass().getName());
			dump.append(")");
			String cmt=fe.getComment();
			if (cmt!=null && cmt.length()>0) {
				dump.append(fmt.genComentario(cmt));
			}
			dumpChildren(dump,fe);
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
			if (fe!=null) {
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
			StringBuilder dump=new StringBuilder();
			boolean firstField=true;
			for (NSField field:rec.getActualFields()) {
				insertIndent(dump,!firstField);
				if (firstField)
					firstField=false;
				dump.append(field.getName());
				if (field instanceof NSRecord) {
					++indent;
					dump.append(dump("",(NSRecord)field));
					--indent;
				}
			}
			return dump.toString();
		}
		
	//		String dump(FlowMap fe) {
	//			StringBuilder dump=new StringBuilder();
	//			defaultDump(dump,fe);
	//			/*
	//			dump.append(fe.toString());
	//			FlowMapSet maps[]=fe.getSetMaps();
	//			if (maps!=null) {
	//				++indent;
	//				for (FlowMapSet fms:maps) {
	//					appendEol(dump);
	//					String childDump=dump(fms);
	//					dump.append(childDump);
	//				}
	//				--indent;
	//			}
	//			dumpChildren(dump,fe);
	//			*/
	//			return dump.toString();
	//		}
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
		
	} // class FlowDumper
	
	static abstract class DumpFormat {
		abstract StringBuilder insertIndent(StringBuilder dump,boolean eol,int indent);
		abstract StringBuilder appendEol(StringBuilder dump);
		abstract String genComentario(String cmt);
		abstract String genLiteral(String cmt);
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
				dump.append("&nbsp;&nbsp;");
			}
			return dump;
		}
		StringBuilder appendEol(StringBuilder dump) {
			dump.append("<br/>");
			return dump;
		}
		String genComentario(String cmt) {
			StringBuilder dump=new StringBuilder();
			dump.append(" // <i class='comentario'>");
			dump.append(escape(cmt));
			dump.append("</i>");
			return dump.toString();
		}
		String genLiteral(String cmt) {
			StringBuilder dump=new StringBuilder();
			dump.append("<code>");
			dump.append(escape(cmt));
			dump.append("</code>");
			return dump.toString();
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
	
		
	// --- <<IS-END-SHARED>> ---
}

