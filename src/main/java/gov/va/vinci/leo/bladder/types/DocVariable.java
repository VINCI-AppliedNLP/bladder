

/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.bladder.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Document Level Variable classification
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * XML source: C:/Users/VHASLC~1/AppData/Local/Temp/4/leoTypeDescription_9fd9eae6-31c2-4ee7-a359-fb63c3b4b84f89778855668149770.xml
 * @generated */
public class DocVariable extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocVariable.class);
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
  protected DocVariable() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocVariable(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocVariable(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocVariable(JCas jcas, int begin, int end) {
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
  //* Feature: Value

  /** getter for Value - gets Value of the document classification
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (DocVariable_Type.featOkTst && ((DocVariable_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.DocVariable");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocVariable_Type)jcasType).casFeatCode_Value);}
    
  /** setter for Value - sets Value of the document classification 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (DocVariable_Type.featOkTst && ((DocVariable_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.DocVariable");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocVariable_Type)jcasType).casFeatCode_Value, v);}    
   
    
  //*--------------*
  //* Feature: Src

  /** getter for Src - gets Source annotation for the value
   * @generated
   * @return value of the feature 
   */
  public Annotation getSrc() {
    if (DocVariable_Type.featOkTst && ((DocVariable_Type)jcasType).casFeat_Src == null)
      jcasType.jcas.throwFeatMissing("Src", "gov.va.vinci.leo.bladder.types.DocVariable");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DocVariable_Type)jcasType).casFeatCode_Src)));}
    
  /** setter for Src - sets Source annotation for the value 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSrc(Annotation v) {
    if (DocVariable_Type.featOkTst && ((DocVariable_Type)jcasType).casFeat_Src == null)
      jcasType.jcas.throwFeatMissing("Src", "gov.va.vinci.leo.bladder.types.DocVariable");
    jcasType.ll_cas.ll_setRefValue(addr, ((DocVariable_Type)jcasType).casFeatCode_Src, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    