

/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.bladder.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import gov.va.vinci.leo.conceptlink.types.Link;


/** Depth of Invasion
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * XML source: C:/Users/VHASLC~1/AppData/Local/Temp/4/leoTypeDescription_9fd9eae6-31c2-4ee7-a359-fb63c3b4b84f89778855668149770.xml
 * @generated */
public class Doi extends Link {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Doi.class);
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
  protected Doi() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Doi(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Doi(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Doi(JCas jcas, int begin, int end) {
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

  /** getter for Value - gets Depth of Invasion Value
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (Doi_Type.featOkTst && ((Doi_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.Doi");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Doi_Type)jcasType).casFeatCode_Value);}
    
  /** setter for Value - sets Depth of Invasion Value 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (Doi_Type.featOkTst && ((Doi_Type)jcasType).casFeat_Value == null)
      jcasType.jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.Doi");
    jcasType.ll_cas.ll_setStringValue(addr, ((Doi_Type)jcasType).casFeatCode_Value, v);}    
  }

    