
/* First created by JCasGen Fri Mar 08 22:18:31 CST 2019 */
package gov.va.vinci.leo.annotationpattern.types;

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
public class AnnotationPatternType_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (AnnotationPatternType_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = AnnotationPatternType_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new AnnotationPatternType(addr, AnnotationPatternType_Type.this);
  			   AnnotationPatternType_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new AnnotationPatternType(addr, AnnotationPatternType_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = AnnotationPatternType.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
 
  /** @generated */
  final Feature casFeat_anchor;
  /** @generated */
  final int     casFeatCode_anchor;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAnchor(int addr) {
        if (featOkTst && casFeat_anchor == null)
      jcas.throwFeatMissing("anchor", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    return ll_cas.ll_getRefValue(addr, casFeatCode_anchor);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnchor(int addr, int v) {
        if (featOkTst && casFeat_anchor == null)
      jcas.throwFeatMissing("anchor", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    ll_cas.ll_setRefValue(addr, casFeatCode_anchor, v);}
    
  
 
  /** @generated */
  final Feature casFeat_anchorPattern;
  /** @generated */
  final int     casFeatCode_anchorPattern;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnchorPattern(int addr) {
        if (featOkTst && casFeat_anchorPattern == null)
      jcas.throwFeatMissing("anchorPattern", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_anchorPattern);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnchorPattern(int addr, String v) {
        if (featOkTst && casFeat_anchorPattern == null)
      jcas.throwFeatMissing("anchorPattern", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    ll_cas.ll_setStringValue(addr, casFeatCode_anchorPattern, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pattern;
  /** @generated */
  final int     casFeatCode_pattern;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPattern(int addr) {
        if (featOkTst && casFeat_pattern == null)
      jcas.throwFeatMissing("pattern", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pattern);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPattern(int addr, String v) {
        if (featOkTst && casFeat_pattern == null)
      jcas.throwFeatMissing("pattern", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
    ll_cas.ll_setStringValue(addr, casFeatCode_pattern, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public AnnotationPatternType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_anchor = jcas.getRequiredFeatureDE(casType, "anchor", "uima.tcas.Annotation", featOkTst);
    casFeatCode_anchor  = (null == casFeat_anchor) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anchor).getCode();

 
    casFeat_anchorPattern = jcas.getRequiredFeatureDE(casType, "anchorPattern", "uima.cas.String", featOkTst);
    casFeatCode_anchorPattern  = (null == casFeat_anchorPattern) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anchorPattern).getCode();

 
    casFeat_pattern = jcas.getRequiredFeatureDE(casType, "pattern", "uima.cas.String", featOkTst);
    casFeatCode_pattern  = (null == casFeat_pattern) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pattern).getCode();

  }
}



    