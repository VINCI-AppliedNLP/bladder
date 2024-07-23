

/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.conceptlink.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** Default Linked Output Type
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * XML source: C:/Users/VHASLC~1/AppData/Local/Temp/4/leoTypeDescription_9fd9eae6-31c2-4ee7-a359-fb63c3b4b84f89778855668149770.xml
 * @generated */
public class Link extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Link.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Link() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Link(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Link(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Link(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: linked

  /** getter for linked - gets Annotations that were 'linked'
   * @generated
   * @return value of the feature 
   */
  public FSArray getLinked() {
    if (Link_Type.featOkTst && ((Link_Type)jcasType).casFeat_linked == null)
      jcasType.jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked)));}
    
  /** setter for linked - sets Annotations that were 'linked' 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLinked(FSArray v) {
    if (Link_Type.featOkTst && ((Link_Type)jcasType).casFeat_linked == null)
      jcasType.jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    jcasType.ll_cas.ll_setRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for linked - gets an indexed value - Annotations that were 'linked'
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getLinked(int i) {
    if (Link_Type.featOkTst && ((Link_Type)jcasType).casFeat_linked == null)
      jcasType.jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked), i)));}

  /** indexed setter for linked - sets an indexed value - Annotations that were 'linked'
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setLinked(int i, Annotation v) { 
    if (Link_Type.featOkTst && ((Link_Type)jcasType).casFeat_linked == null)
      jcasType.jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Link_Type)jcasType).casFeatCode_linked), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    