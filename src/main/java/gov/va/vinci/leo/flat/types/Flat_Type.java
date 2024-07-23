
/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.flat.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * @generated */
public class Flat_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Flat_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Flat_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Flat(addr, Flat_Type.this);
  			   Flat_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Flat(addr, Flat_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Flat.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.flat.types.Flat");
 
  /** @generated */
  final Feature casFeat_keys;
  /** @generated */
  final int     casFeatCode_keys;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getKeys(int addr) {
        if (featOkTst && casFeat_keys == null)
      jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    return ll_cas.ll_getRefValue(addr, casFeatCode_keys);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setKeys(int addr, int v) {
        if (featOkTst && casFeat_keys == null)
      jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    ll_cas.ll_setRefValue(addr, casFeatCode_keys, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getKeys(int addr, int i) {
        if (featOkTst && casFeat_keys == null)
      jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setKeys(int addr, int i, String v) {
        if (featOkTst && casFeat_keys == null)
      jcas.throwFeatMissing("keys", "gov.va.vinci.leo.flat.types.Flat");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_keys), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_values;
  /** @generated */
  final int     casFeatCode_values;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getValues(int addr) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    return ll_cas.ll_getRefValue(addr, casFeatCode_values);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValues(int addr, int v) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    ll_cas.ll_setRefValue(addr, casFeatCode_values, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getValues(int addr, int i) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setValues(int addr, int i, String v) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "gov.va.vinci.leo.flat.types.Flat");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Flat_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_keys = jcas.getRequiredFeatureDE(casType, "keys", "uima.cas.StringArray", featOkTst);
    casFeatCode_keys  = (null == casFeat_keys) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_keys).getCode();

 
    casFeat_values = jcas.getRequiredFeatureDE(casType, "values", "uima.cas.StringArray", featOkTst);
    casFeatCode_values  = (null == casFeat_values) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_values).getCode();

  }
}



    