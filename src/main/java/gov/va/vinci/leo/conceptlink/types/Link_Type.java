
/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.conceptlink.types;

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

/** Default Linked Output Type
 * Updated by JCasGen Fri Mar 08 22:18:31 CST 2019
 * @generated */
public class Link_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Link_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Link_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Link(addr, Link_Type.this);
  			   Link_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Link(addr, Link_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Link.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.conceptlink.types.Link");
 
  /** @generated */
  final Feature casFeat_linked;
  /** @generated */
  final int     casFeatCode_linked;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLinked(int addr) {
        if (featOkTst && casFeat_linked == null)
      jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    return ll_cas.ll_getRefValue(addr, casFeatCode_linked);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLinked(int addr, int v) {
        if (featOkTst && casFeat_linked == null)
      jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    ll_cas.ll_setRefValue(addr, casFeatCode_linked, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getLinked(int addr, int i) {
        if (featOkTst && casFeat_linked == null)
      jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setLinked(int addr, int i, int v) {
        if (featOkTst && casFeat_linked == null)
      jcas.throwFeatMissing("linked", "gov.va.vinci.leo.conceptlink.types.Link");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_linked), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Link_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_linked = jcas.getRequiredFeatureDE(casType, "linked", "uima.cas.FSArray", featOkTst);
    casFeatCode_linked  = (null == casFeat_linked) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_linked).getCode();

  }
}



    