package wx.insider.log;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.wm.lang.ns.NSService;
import com.wm.app.b2b.server.InvokeState;
import java.util.Stack;
import javax.xml.bind.DatatypeConverter;
// --- <<IS-END-IMPORTS>> ---

public final class pub

{
	// ---( internal utility methods )---

	final static pub _instance = new pub();

	static pub _newInstance() { return new pub(); }

	static pub _cast(Object o) { return (pub)o; }

	// ---( server methods )---




	public static final void describeError (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(describeError)>> ---
		// @sigtype java 3.5
		// [i] recref:0:required lastError pub.event:exceptionInfo
		// [o] field:0:required mensajeError
		// [o] field:0:required descripcionError
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			IData lastError=IDataUtil.getIData(pipelineCursor,"lastError");
		pipelineCursor.destroy();
		
		StringBuilder descripcion=new StringBuilder();
		
		String[] pilaLlamante=obtenerPilaLlamante();
		String mensaje=lastError_describir(descripcion,lastError,true,pilaLlamante);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "descripcionError", descripcion.toString() );
		IDataUtil.put( pipelineCursor_1, "mensajeError", mensaje );
		pipelineCursor_1.destroy();
			
			
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	protected static final String LASTERROR_CAUSADO_POR="lastError causado por:";
	
	/*
	 * // OJO - NO SOPORTADO
	 * 
	 */
	protected static String[] obtenerPilaLlamante() {
		
		Stack callStack = InvokeState.getCurrentState().getCallStack();
		List<String> pilaLlamante=new ArrayList<String>();
		if (callStack!=null) {
			NSService currentSvc = Service.getServiceEntry();
			int i=callStack.indexOf(currentSvc) - 3;
			for (;i>=0;--i) {
				  NSService svc=(NSService) callStack.elementAt(i);
				  pilaLlamante.add(svc.toString());
			}
		}
		
		return pilaLlamante.toArray(new String[pilaLlamante.size()]);
	}
	
	
	protected static String lastError_describir(StringBuilder descripcion,IData lastError,boolean incluyeErrorCorto,String[] pilaLlamante) {
		String mensaje=null;
		if (lastError!=null) {
			IDataCursor ec=lastError.getCursor();
			String error=IDataUtil.getString(ec,"error");
			if (error!=null) {
					mensaje=error;
					if (incluyeErrorCorto) {
						if (descripcion.length()>0)
							descripcion.append('\n');
						descripcion.append(error);
					}
			}
			String errorDump=IDataUtil.getString(ec, "errorDump");
			
			// Cuando el errorDump termina por el error, es que no aporta nada interesante, as\u00ED que lo ignoramos
			if (errorDump!=null && error!=null && !errorDump.endsWith(error))
				lastError_describirErrorDump(descripcion,errorDump);
			
			lastError_describirCallStack(descripcion,IDataUtil.getIDataArray(ec, "callStack"),pilaLlamante);
			
			IData nestedErrorInfo=IDataUtil.getIData(ec,"nestedErrorInfo");
			if (nestedErrorInfo!=null) {
				// Pel\u00EDn redundante, esto
				String nestedError=lastError_getError(nestedErrorInfo);
				if (error==null || nestedError!=null && !error.equals(nestedError)) {
					if (descripcion.length()>0)
						descripcion.append('\n');
					descripcion.append(LASTERROR_CAUSADO_POR);
					lastError_describir(descripcion,nestedErrorInfo,true,null);
				}
			}
		}
		if (mensaje==null)
			mensaje="(Error sin texto)";
		return mensaje;
	}
	
	
	
	
	protected static void lastError_describirCallStack(StringBuilder descripcion,IData/*callStackItem*/[]callStack,String[] pilaLlamante) {
		if (callStack!=null) {
		int I=callStack.length;
		
		if (descripcion.length()>0)
			descripcion.append('\n');
		descripcion.append("Pila de servicios:\n");
		
		for (int i=0;i<I;++i) {
			IData/*callStackItem*/ callStackItem=callStack[i];
			descripcion.append("  ");
			
			if (callStackItem!=null) {
				IDataCursor csic=callStackItem.getCursor();
				String service=IDataUtil.getString(csic,"service");
				String flowStep=IDataUtil.getString(csic,"flowStep");
				descripcion.append(service);
				descripcion.append(flowStep);
			}
			descripcion.append("\n");
		}
		if (pilaLlamante!=null) {
			for (String s:pilaLlamante) {
				descripcion.append("  ");
				descripcion.append(s);
				descripcion.append("\n");
			}
		}
		}
	}
	
	protected static void lastError_describirErrorDump(StringBuilder descripcion,String errorDump) {
		if (errorDump!=null) {
			if (descripcion.length()>0)
				descripcion.append('\n');
			descripcion.append("ErrorDump:\n");
			descripcion.append(errorDump);
			descripcion.append("\n");
		}
	}
	
	
	
	
	protected static String lastError_getError(IData lastError) {
		IDataCursor ec=lastError.getCursor();
		String error=lastError_getError(ec);
		ec.destroy();
		return error;
	}
	
	protected static String lastError_getError(IDataCursor ec) {
		return IDataUtil.getString(ec,"error");
	}
	
		
		
	// --- <<IS-END-SHARED>> ---
}

