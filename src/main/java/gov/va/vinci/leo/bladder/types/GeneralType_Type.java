
/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.bladder.types;

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

/** Parent Type for instance level output
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * @generated */
public class GeneralType_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GeneralType_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GeneralType_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GeneralType(addr, GeneralType_Type.this);
  			   GeneralType_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GeneralType(addr, GeneralType_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GeneralType.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.bladder.types.GeneralType");
 
  /** @generated */
  final Feature casFeat_VariableType;
  /** @generated */
  final int     casFeatCode_VariableType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getVariableType(int addr) {
        if (featOkTst && casFeat_VariableType == null)
      jcas.throwFeatMissing("VariableType", "gov.va.vinci.leo.bladder.types.GeneralType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_VariableType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setVariableType(int addr, String v) {
        if (featOkTst && casFeat_VariableType == null)
      jcas.throwFeatMissing("VariableType", "gov.va.vinci.leo.bladder.types.GeneralType");
    ll_cas.ll_setStringValue(addr, casFeatCode_VariableType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_VariableValue;
  /** @generated */
  final int     casFeatCode_VariableValue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getVariableValue(int addr) {
        if (featOkTst && casFeat_VariableValue == null)
      jcas.throwFeatMissing("VariableValue", "gov.va.vinci.leo.bladder.types.GeneralType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_VariableValue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setVariableValue(int addr, String v) {
        if (featOkTst && casFeat_VariableValue == null)
      jcas.throwFeatMissing("VariableValue", "gov.va.vinci.leo.bladder.types.GeneralType");
    ll_cas.ll_setStringValue(addr, casFeatCode_VariableValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GeneralType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_VariableType = jcas.getRequiredFeatureDE(casType, "VariableType", "uima.cas.String", featOkTst);
    casFeatCode_VariableType  = (null == casFeat_VariableType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_VariableType).getCode();

 
    casFeat_VariableValue = jcas.getRequiredFeatureDE(casType, "VariableValue", "uima.cas.String", featOkTst);
    casFeatCode_VariableValue  = (null == casFeat_VariableValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_VariableValue).getCode();

  }
}



    