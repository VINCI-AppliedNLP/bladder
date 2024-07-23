
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

/** Document Level Variable classification
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * @generated */
public class DocVariable_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocVariable_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocVariable_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocVariable(addr, DocVariable_Type.this);
  			   DocVariable_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocVariable(addr, DocVariable_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocVariable.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.bladder.types.DocVariable");
 
  /** @generated */
  final Feature casFeat_Value;
  /** @generated */
  final int     casFeatCode_Value;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.DocVariable");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Value);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_Value == null)
      jcas.throwFeatMissing("Value", "gov.va.vinci.leo.bladder.types.DocVariable");
    ll_cas.ll_setStringValue(addr, casFeatCode_Value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Src;
  /** @generated */
  final int     casFeatCode_Src;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSrc(int addr) {
        if (featOkTst && casFeat_Src == null)
      jcas.throwFeatMissing("Src", "gov.va.vinci.leo.bladder.types.DocVariable");
    return ll_cas.ll_getRefValue(addr, casFeatCode_Src);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSrc(int addr, int v) {
        if (featOkTst && casFeat_Src == null)
      jcas.throwFeatMissing("Src", "gov.va.vinci.leo.bladder.types.DocVariable");
    ll_cas.ll_setRefValue(addr, casFeatCode_Src, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocVariable_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Value = jcas.getRequiredFeatureDE(casType, "Value", "uima.cas.String", featOkTst);
    casFeatCode_Value  = (null == casFeat_Value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Value).getCode();

 
    casFeat_Src = jcas.getRequiredFeatureDE(casType, "Src", "uima.tcas.Annotation", featOkTst);
    casFeatCode_Src  = (null == casFeat_Src) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Src).getCode();

  }
}



    