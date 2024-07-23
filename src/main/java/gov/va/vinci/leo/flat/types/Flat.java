

/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.flat.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * XML source: C:/Users/VHASLC~1/AppData/Local/Temp/4/leoTypeDescription_9fd9eae6-31c2-4ee7-a359-fb63c3b4b84f89778855668149770.xml
 * @generated */
public class Flat extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Flat.class);
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
  protected Flat() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Flat(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Flat(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Flat(JCas jcas, int begin, int end) {
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
  //* Feature: keys

  /** getter for keys - gets Property Keys Array
   * @generated
   * @return value of the feature 
   */
  public StringArray getKeys() {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_keys == null)
      jcasType.jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys)));}
    
  /** setter for keys - sets Property Keys Array 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKeys(StringArray v) {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_keys == null)
      jcasType.jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.ll_cas.ll_setRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for keys - gets an indexed value - Property Keys Array
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getKeys(int i) {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_keys == null)
      jcasType.jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys), i);}

  /** indexed setter for keys - sets an indexed value - Property Keys Array
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setKeys(int i, String v) { 
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_keys == null)
      jcasType.jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_keys), i, v);}
   
    
  //*--------------*
  //* Feature: values

  /** getter for values - gets Property Values Array
   * @generated
   * @return value of the feature 
   */
  public StringArray getValues() {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values)));}
    
  /** setter for values - sets Property Values Array 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValues(StringArray v) {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.ll_cas.ll_setRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for values - gets an indexed value - Property Values Array
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getValues(int i) {
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values), i);}

  /** indexed setter for values - sets an indexed value - Property Values Array
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setValues(int i, String v) { 
    if (Flat_Type.featOkTst && ((Flat_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Flat_Type)jcasType).casFeatCode_values), i, v);}
  }

    