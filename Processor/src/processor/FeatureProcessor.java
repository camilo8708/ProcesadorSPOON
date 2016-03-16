package processor;


import generated.Alt;
import generated.And;
import generated.Constraints;
import generated.Feature;
import generated.FeatureModel;
import generated.Imp;
import generated.Not;
import generated.ObjectFactory;
import generated.Or;
import generated.Parent;
import generated.Rule;
import generated.Struct;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import uniandes.cupi2.biblioteca.mundo.implementacion1.FabricaBiblioteca;
import annotation.annotationAlt;
import annotation.annotationAnd;
import annotation.annotationExclude;
import annotation.annotationExtends;
import annotation.annotationFeature;
import annotation.annotationOr;

public class FeatureProcessor extends AbstractProcessor<CtAnnotation<annotationFeature>> {
	FeatureModel fm;
	Constraints constrain;
	
	ArrayList<annotationFeature> pendienteFeature;
	ArrayList<annotationAnd> pendienteAnd;
	ArrayList<annotationAlt> pendienteAlt;
	ArrayList<annotationOr> pendienteOr;
	
	@Override
	public void init() {
		ObjectFactory factory = new ObjectFactory();
		fm = factory.createFeatureModel();
		Struct struct = factory.createStruct();
		
		And root = factory.createAnd();
		root.setName("Biblioteca");
		struct.setAnd(root);
		fm.setStruct(struct);
		
		constrain = factory.createConstraints();
		fm.setConstraints(constrain);
		
		pendienteFeature = new ArrayList<annotationFeature>();
		pendienteAnd = new ArrayList<annotationAnd>();
		pendienteAlt = new ArrayList<annotationAlt>();
		pendienteOr = new ArrayList<annotationOr>();
		
		// TODO Auto-generated method stub
		super.init();
	}
	
	
	public void process(CtAnnotation<annotationFeature> annotation) {
		ObjectFactory factory = new ObjectFactory();
		if(annotation.getActualAnnotation() instanceof annotationFeature){
       		createFeature(annotation.getActualAnnotation());
		} else if(annotation.getActualAnnotation() instanceof annotationAnd){
			createAnd((annotationAnd) annotation.getActualAnnotation());
        } else if(annotation.getActualAnnotation() instanceof annotationAlt){
        	createAlt((annotationAlt) annotation.getActualAnnotation());
        } else if(annotation.getActualAnnotation() instanceof annotationOr){
        	createOr((annotationOr) annotation.getActualAnnotation());
        } else if(annotation.getActualAnnotation() instanceof annotationExclude){
        	Not not = factory.createNot();
        	not.setVar(((annotationExclude)annotation.getActualAnnotation()).destino());
        	
        	Imp imp = factory.createImp();
        	imp.getVarOrNot().add(((annotationExclude)annotation.getActualAnnotation()).origen());
        	imp.getVarOrNot().add(not);
        	
        	Rule rule = factory.createRule();
        	rule.getImp().add(imp);
        	constrain.getRule().add(rule);
        } else if(annotation.getActualAnnotation() instanceof annotationExtends){
        	Imp imp = factory.createImp();
        	imp.getVarOrNot().add(((annotationExtends)annotation.getActualAnnotation()).origen());
        	imp.getVarOrNot().add(((annotationExtends)annotation.getActualAnnotation()).destino());
        	
        	Rule rule = factory.createRule();
        	rule.getImp().add(imp);
        	constrain.getRule().add(rule);
        }
	}	

	@Override
	public void processingDone() {
		processFinal();
		// TODO Auto-generated method stub
		jaxbWriter(fm, "./resources/model.xml", "./resources/featureide.xsd");
		super.processingDone();
	}
	
	
	/**
	 * Writes the contents of a JAXB model in an xml file with identation and
	 * blank spaces
	 * 
	 * @param root
	 *            the root of the object to write
	 * @param path
	 *            destination of the file to create
	 */
	private void jaxbWriter(Object root, String path, String schema) {
		try {
			JAXBContext context = JAXBContext.newInstance(root.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schema);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(root, new FileWriter(path));
		} catch (JAXBException e) {
			System.err
					.println("Error while preparing to write the JAXB model in: "
							+ path);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error while writting the JAXB model in: "
					+ path);
			e.printStackTrace();
		}
	}
	
	private void createFeature(annotationFeature annotation){
		ObjectFactory factory = new ObjectFactory();
		//Buscamos el padre
   		Parent padre = buscarFeature(fm.getStruct().getAnd(),annotation.parent());
   		if(padre != null || annotation.parent().equals("")){
   			Feature feature = factory.createFeature();
   			feature.setName(annotation.featureName());
   			feature.setMandatory(annotation.mandaroty());
   			if(padre != null)
   				padre.getAndOrAltOrOr().add(feature);
   			else
   				fm.getStruct().getAnd().getAndOrAltOrOr().add(feature);
   		} else{
   			pendienteFeature.add(annotation);
   		}
	}
	
	private And createAnd(annotationAnd annotation){
		ObjectFactory factory = new ObjectFactory();
		Parent padre = buscarFeature(fm.getStruct().getAnd(), annotation.parent());
		if(annotation.parent().equals("") || padre != null){
			And and	 = factory.createAnd();
			and.setName(annotation.featureName());
			and.setMandatory(annotation.mandaroty());
			setHijosFeature(and);
			setHijosAnd(and);
			setHijosAlt(and);
			setHijosOr(and);
			// Asignamos padre
			if(padre != null)
				padre.getAndOrAltOrOr().add(and);
			else
				fm.getStruct().getAnd().getAndOrAltOrOr().add(and);
			return and;
		} else{
			pendienteAnd.add(annotation);
			return null;
		}
	}
	
	private Alt createAlt(annotationAlt annotation){
		ObjectFactory factory = new ObjectFactory();
		Parent padre = buscarFeature(fm.getStruct().getAnd(), annotation.parent());
		if(annotation.parent().equals("") || padre != null){
			Alt alt	 = factory.createAlt();
			alt.setName(annotation.featureName());
			alt.setMandatory(annotation.mandaroty());
			setHijosFeature(alt);
			setHijosAnd(alt);
			setHijosAlt(alt);
			setHijosOr(alt);
			// Asignamos padre
			if(padre != null)
				padre.getAndOrAltOrOr().add(alt);
			else
				fm.getStruct().getAnd().getAndOrAltOrOr().add(alt);
			return alt;
		} else{
			pendienteAlt.add(annotation);
			return null;
		}
	}
	
	private Or createOr(annotationOr annotation){
		ObjectFactory factory = new ObjectFactory();
		Parent padre = buscarFeature(fm.getStruct().getAnd(), annotation.parent());
		if(annotation.parent().equals("") || padre != null){
			Or or = factory.createOr();
			or.setName(annotation.featureName());
			or.setMandatory(annotation.mandaroty());
			setHijosFeature(or);
			setHijosAnd(or);
			setHijosAlt(or);
			setHijosOr(or);
			// Asignamos padre
			if(padre != null)
				padre.getAndOrAltOrOr().add(or);
			else
				fm.getStruct().getAnd().getAndOrAltOrOr().add(or);
			return or;
		} else{
			pendienteOr.add(annotation);
			return null;
		}
	}
	
	private Parent buscarFeature(Parent buscar, String nombre){
		Iterator<Object> lista = buscar.getAndOrAltOrOr().iterator();
		while(lista.hasNext()){
			Object obj = lista.next();
			if(obj instanceof Parent){
				Parent feature = (Parent)obj;		
				if(feature.getName().equals(nombre)){
					return feature;
				}else{
					if(buscarFeature(feature, nombre) != null)
						return buscarFeature(feature, nombre);
				}
			}
		}
		return null;
	}
	
	private void setHijosAnd(Parent padre){
		int ct = 0;
		while(ct < pendienteAnd.size()){
			if(pendienteAnd.get(ct).parent().equals(padre.getName())){
				And nodo = createAnd(pendienteAnd.get(ct));
	       		padre.getAndOrAltOrOr().add(nodo);
				pendienteAnd.remove(ct);
				ct--;
			}
			ct++;
		}
	}
	
	private void setHijosAlt(Parent padre){
		int ct = 0;
		while(ct < pendienteAlt.size()){
			if(pendienteAlt.get(ct).parent().equals(padre.getName())){
				Alt nodo = createAlt(pendienteAlt.get(ct));
	       		padre.getAndOrAltOrOr().add(nodo);
				pendienteAlt.remove(ct);
				ct--;
			}
			ct++;
		}
	}
	
	private void setHijosOr(Parent padre){
		int ct = 0;
		while(ct < pendienteOr.size()){
			if(pendienteOr.get(ct).parent().equals(padre.getName())){
				Or nodo = createOr(pendienteOr.get(ct));
	       		padre.getAndOrAltOrOr().add(nodo);
				pendienteOr.remove(ct);
				ct--;
			}
			ct++;
		}
	}
	
	private void setHijosFeature(Parent padre){
		int ct = 0;
		while(ct < pendienteFeature.size()){
			if(pendienteFeature.get(ct).parent().equals(padre.getName())){
				ObjectFactory factory = new ObjectFactory();
				generated.Feature nodo = factory.createFeature();
	       		nodo.setName(pendienteFeature.get(ct).featureName());
	       		padre.getAndOrAltOrOr().add(nodo);
	   
				pendienteFeature.remove(ct);
				ct--;
			}
			ct++;
		}
	}
	
	private void processFinal(){
		//Creamos And
		int ct = 0;
		while(ct < pendienteAnd.size()){
			createAnd(pendienteAnd.get(ct));
			pendienteAnd.remove(ct);
		}
		
		//Creamos Alt
		ct = 0;
		while(ct < pendienteAlt.size()){
			createAlt(pendienteAlt.get(ct));
			pendienteAlt.remove(ct);
		}
		
		//Creamos Or
		ct = 0;
		while(ct < pendienteOr.size()){
			createOr(pendienteOr.get(ct));
			pendienteOr.remove(ct);
		}
		
		//Creamos Features
		ct = 0;
		while(ct < pendienteFeature.size()){
			createFeature(pendienteFeature.get(ct));
			pendienteFeature.remove(ct);
		}
	}
}
