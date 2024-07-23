

/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.bladder.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Parent Type for instance level output
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * XML source: C:/Users/VHASLC~1/AppData/Local/Temp/4/leoTypeDescription_9fd9eae6-31c2-4ee7-a359-fb63c3b4b84f89778855668149770.xml
 * @generated */
public class GeneralType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneralType.class);
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
  protected GeneralType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GeneralType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GeneralType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GeneralType(JCas jcas, int begin, int end) {
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
  //* Feature: VariableType

  /** getter for VariableType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVariableType() {
    if (GeneralType_Type.featOkTst && ((GeneralType_Type)jcasType).casFeat_VariableType == null)
      jcasType.jcas.throwFeatMissing("VariableType", "gov.va.vinci.leo.bladder.types.GeneralType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneralType_Type)jcasType).casFeatCode_VariableType);}
    
  /** setter for VariableType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVariableType(String v) {
    if (GeneralType_Type.featOkTst && ((GeneralType_Type)jcasType).casFeat_VariableType == null)
      jcasType.jcas.throwFeatMissing("VariableType", "gov.va.vinci.leo.bladder.types.GeneralType");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneralType_Type)jcasType).casFeatCode_VariableType, v);}    
   
    
  //*--------------*
  //* Feature: VariableValue

  /** getter for VariableValue - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVariableValue() {
    if (GeneralType_Type.featOkTst && ((GeneralType_Type)jcasType).casFeat_VariableValue == null)
      jcasType.jcas.throwFeatMissing("VariableValue", "gov.va.vinci.leo.bladder.types.GeneralType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneralType_Type)jcasType).casFeatCode_VariableValue);}
    
  /** setter for VariableValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVariableValue(String v) {
    if (GeneralType_Type.featOkTst && ((GeneralType_Type)jcasType).casFeat_VariableValue == null)
      jcasType.jcas.throwFeatMissing("VariableValue", "gov.va.vinci.leo.bladder.types.GeneralType");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneralType_Type)jcasType).casFeatCode_VariableValue, v);}    
  }

    