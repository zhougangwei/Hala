precision highp float;
uniform sampler2D inputImageTexture;
varying vec2 textureCoordinate;

uniform vec2 singleStepOffset;
uniform highp vec4 params;

uniform float distanceNormalizationFactor; //磨皮指数
uniform float  smoothDegree; //磨皮系数
uniform float  noise; //去噪
uniform float  brightness;//美白
uniform float  redDegree;//红润

//基础功能
uniform int  isUseHigtLevel;//是否启用高级美颜
uniform int  bBeauty;//是否启用美颜
uniform int  isMirror;//是否镜像

const highp vec3 W = vec3(0.299,0.587,0.114);
const mat3 saturateMatrix = mat3(
                                 1.1102,-0.0598,-0.061,
                                 -0.0774,1.0826,-0.1186,
                                 -0.0228,-0.0228,1.1772);

float hardlight(float color)
{
	if(color <= 0.5)
	{
		color = color * color * 2.0;
	}
	else
	{
		color = 1.0 - ((1.0 - color)*(1.0 - color) * 2.0);
	}
	return color;
}
//饱和度用到的值
///////////////////////////////////////////////////////////////////////////
uniform mediump float saturation;
const vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);
///////////////////////////////////////////////////////////////////////////

//白平衡用到的值
///////////////////////////////////////////////////////////////////////////
uniform float temperature;
uniform float tint;

const vec3 warmFilter = vec3(0.93, 0.54, 0.0);

const mat3 RGBtoYIQ = mat3(0.299, 0.587, 0.114, 0.596, -0.274, -0.322, 0.212, -0.523, 0.311);
const mat3 YIQtoRGB = mat3(1.0, 0.956, 0.621, 1.0, -0.272, -0.647, 1.0, -1.105, 1.702);
///////////////////////////////////////////////////////////////////////////

 //基础磨皮
 ////////////////////////////////////////////////////////////////////////////////////////////
 float baseSmooth(vec2 textureCoordinateToUse,sampler2D inputImageTexInput)
 {
     vec2 blurCoordinates[24];

     blurCoordinates[0] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.0, -10.0);
     blurCoordinates[1] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.0, 10.0);
     blurCoordinates[2] = textureCoordinateToUse.xy + singleStepOffset * vec2(-10.0, 0.0);
     blurCoordinates[3] = textureCoordinateToUse.xy + singleStepOffset * vec2(10.0, 0.0);

     blurCoordinates[4] = textureCoordinateToUse.xy + singleStepOffset * vec2(5.0, -8.0);
     blurCoordinates[5] = textureCoordinateToUse.xy + singleStepOffset * vec2(5.0, 8.0);
     blurCoordinates[6] = textureCoordinateToUse.xy + singleStepOffset * vec2(-5.0, 8.0);
     blurCoordinates[7] = textureCoordinateToUse.xy + singleStepOffset * vec2(-5.0, -8.0);

     blurCoordinates[8] = textureCoordinateToUse.xy + singleStepOffset * vec2(8.0, -5.0);
     blurCoordinates[9] = textureCoordinateToUse.xy + singleStepOffset * vec2(8.0, 5.0);
     blurCoordinates[10] = textureCoordinateToUse.xy + singleStepOffset * vec2(-8.0, 5.0);
     blurCoordinates[11] = textureCoordinateToUse.xy + singleStepOffset * vec2(-8.0, -5.0);

     blurCoordinates[12] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.0, -6.0);
     blurCoordinates[13] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.0, 6.0);
     blurCoordinates[14] = textureCoordinateToUse.xy + singleStepOffset * vec2(6.0, 0.0);
     blurCoordinates[15] = textureCoordinateToUse.xy + singleStepOffset * vec2(-6.0, 0.0);

     blurCoordinates[16] = textureCoordinateToUse.xy + singleStepOffset * vec2(-4.0, -4.0);
     blurCoordinates[17] = textureCoordinateToUse.xy + singleStepOffset * vec2(-4.0, 4.0);
     blurCoordinates[18] = textureCoordinateToUse.xy + singleStepOffset * vec2(4.0, -4.0);
     blurCoordinates[19] = textureCoordinateToUse.xy + singleStepOffset * vec2(4.0, 4.0);

     blurCoordinates[20] = textureCoordinateToUse.xy + singleStepOffset * vec2(-2.0, -2.0);
     blurCoordinates[21] = textureCoordinateToUse.xy + singleStepOffset * vec2(-2.0, 2.0);
     blurCoordinates[22] = textureCoordinateToUse.xy + singleStepOffset * vec2(2.0, -2.0);
     blurCoordinates[23] = textureCoordinateToUse.xy + singleStepOffset * vec2(2.0, 2.0);


     float sampleColor = texture2D(inputImageTexInput, textureCoordinateToUse).g * 22.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[0]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[1]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[2]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[3]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[4]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[5]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[6]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[7]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[8]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[9]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[10]).g;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[11]).g;

     sampleColor += texture2D(inputImageTexInput, blurCoordinates[12]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[13]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[14]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[15]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[16]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[17]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[18]).g * 2.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[19]).g * 2.0;

     sampleColor += texture2D(inputImageTexInput, blurCoordinates[20]).g * 3.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[21]).g * 3.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[22]).g * 3.0;
     sampleColor += texture2D(inputImageTexInput, blurCoordinates[23]).g * 3.0;

     sampleColor = sampleColor / 62.0;

     return sampleColor;
 }

 vec4 baseSmooth2(sampler2D inputImageTexInput,vec2 textureCoordinateToUse )
 {
     vec3 centralColor = texture2D(inputImageTexInput, textureCoordinateToUse).rgb;

     float sampleColor = baseSmooth(textureCoordinateToUse,inputImageTexInput);

     float highpass = centralColor.g - sampleColor + 0.5;

     for(int i = 0; i < 5;i++)
     {
         highpass = hardlight(highpass);
     }

     float lumance  = dot(centralColor, W);

     float alpha    = pow(lumance, 1.0);

     vec3 smoothColor = centralColor + (centralColor-vec3(highpass))*alpha*0.1;

    	vec3 lvse = vec3(1.0)-(vec3(1.0)-smoothColor)*(vec3(1.0)-centralColor);
    	vec3 bianliang = max(smoothColor, centralColor);
    	vec3 rouguang = 2.0*centralColor*smoothColor + centralColor*centralColor - 2.0*centralColor*centralColor*smoothColor;

    	vec3 mainColor = mix(smoothColor, lvse, brightness*2.0);
    	mainColor      = mix(mainColor, bianliang, alpha);
//    	mainColor      = mix(mainColor, rouguang, brightness);

     return vec4(mainColor,1.0);
 }

 //高级美颜
 vec4 highSmooth(sampler2D inputImageTexInput,vec2 textureCoordinateToUse )
 {
     vec4 centralColor = texture2D(inputImageTexInput, textureCoordinateToUse);
     vec2 blurCoordinates[24];
     vec2 blurStep;
     float gaussianWeightTotal;
     vec4 sum;
     vec4 sampleColor;
     float distanceFromCentralColor;
     float gaussianWeight;

     blurCoordinates[0] = textureCoordinateToUse.xy + singleStepOffset * vec2(-4.,-4.);
     blurCoordinates[1] = textureCoordinateToUse.xy + singleStepOffset * vec2(4.,-4.);
     blurCoordinates[2] = textureCoordinateToUse.xy + singleStepOffset * vec2(-4.,4.);
     blurCoordinates[3] = textureCoordinateToUse.xy + singleStepOffset * vec2(4.,4.);

     blurCoordinates[4] = textureCoordinateToUse.xy + singleStepOffset * vec2(-3.,-3.);
     blurCoordinates[5] = textureCoordinateToUse.xy + singleStepOffset * vec2(3.,-3.);
     blurCoordinates[6] = textureCoordinateToUse.xy + singleStepOffset * vec2(-3.,3.);
     blurCoordinates[7] = textureCoordinateToUse.xy + singleStepOffset * vec2(3.,3.);

     blurCoordinates[8] = textureCoordinateToUse.xy + singleStepOffset * vec2(-2.,-2.);
     blurCoordinates[9] = textureCoordinateToUse.xy + singleStepOffset * vec2(2.,-2.);
     blurCoordinates[10] = textureCoordinateToUse.xy + singleStepOffset * vec2(2.,2.);
     blurCoordinates[11] = textureCoordinateToUse.xy + singleStepOffset * vec2(-2.,2.);

     blurCoordinates[12] = textureCoordinateToUse.xy + singleStepOffset * vec2(1.,1.);
     blurCoordinates[13] = textureCoordinateToUse.xy + singleStepOffset * vec2(1.,-1.);
     blurCoordinates[14] = textureCoordinateToUse.xy + singleStepOffset * vec2(-1.,1.);
     blurCoordinates[15] = textureCoordinateToUse.xy + singleStepOffset * vec2(-1.,-1.);

     blurCoordinates[16] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.,-4.);
     blurCoordinates[17] = textureCoordinateToUse.xy + singleStepOffset * vec2(4.,0.);
     blurCoordinates[18] = textureCoordinateToUse.xy + singleStepOffset * vec2(-4.,0);
     blurCoordinates[19] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.,4.);

     blurCoordinates[20] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.,-2.);
     blurCoordinates[21] = textureCoordinateToUse.xy + singleStepOffset * vec2(2.,0.);
     blurCoordinates[22] = textureCoordinateToUse.xy + singleStepOffset * vec2(-2.,0);
     blurCoordinates[23] = textureCoordinateToUse.xy + singleStepOffset * vec2(0.,2.);

     gaussianWeightTotal = 0.4;
     sum = centralColor * 0.4;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[0]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[1]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[2]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[3]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[4]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[5]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[6]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[7]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[8]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[9]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[10]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[11]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[12]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[13]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[14]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[15]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[16]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.07 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[17]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.07 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[18]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.07 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[19]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.07 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     sampleColor = texture2D(inputImageTexInput, blurCoordinates[20]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.11 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[21]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.11 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[22]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.11 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;
     sampleColor = texture2D(inputImageTexInput, blurCoordinates[23]);
     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
     gaussianWeight = 0.11 * (1.0 - distanceFromCentralColor);
     gaussianWeightTotal += gaussianWeight;
     sum += sampleColor * gaussianWeight;

     highp vec4 bilateral = sum / gaussianWeightTotal;

     return bilateral;
 }
 ////////////////////////////////////////////////////////////////////////////////////////////

void main()
{
    if(bBeauty ==  0)
    {
         //关闭美颜
         if(isMirror == 1)
         {
             gl_FragColor = texture2D(inputImageTexture, vec2(1.0 -textureCoordinate.x,textureCoordinate.y));
         }
         else
         {
            gl_FragColor = texture2D(inputImageTexture, textureCoordinate);
         }
         return;
    }
    if(isUseHigtLevel > 0 )
    {
        vec4 centralColor = texture2D(inputImageTexture, textureCoordinate);
        highp vec4 bilateral  = baseSmooth2(inputImageTexture, textureCoordinate); //高斯模糊
//        highp vec4 bilateral  = highSmooth(inputImageTexture, textureCoordinate);//双边滤波
        highp vec4 smooth1;
        lowp float r = centralColor.r;
        lowp float g = centralColor.g;
        lowp float b = centralColor.b;

        //肤色判断
        if( r > 0.3725 &&
           g > 0.1568 &&
           b > 0.0784 &&
           r > b &&
           r > g &&
           (max(max(r, g), b) - min(min(r, g), b)) > 0.0588 &&
           abs(r-g) > 0.0588)
        {
            //磨皮
            smooth1 =(1.0 - smoothDegree) * (centralColor - bilateral) + bilateral;
        }
        else
        {
            //去噪
            smooth1 =(1.0 - noise) * (centralColor - bilateral) + bilateral;

        }

        //红润
        vec3 satcolor = smooth1.rgb * saturateMatrix;
        smooth1 = vec4(mix(smooth1.rgb, satcolor, redDegree*0.5),1.0);

        //美白
        vec4 textureColor = vec4(smooth1.rgb ,1.0);
        textureColor.r = clamp(pow(textureColor.r, 1.0 - brightness*0.5),0.0,1.0);
        textureColor.g = clamp(pow(textureColor.g, 1.0 - brightness*0.5),0.0,1.0);
        textureColor.b = clamp(pow(textureColor.b, 1.0 - brightness*0.5),0.0,1.0);

        //饱和度
        float luminance = dot(textureColor.rgb, luminanceWeighting);
        vec3 greyScaleColor = vec3(luminance);
        vec4 source = vec4(mix(greyScaleColor, textureColor.rgb, saturation), textureColor.w);

        //白平衡
        mediump vec3 yiq = RGBtoYIQ * source.rgb;
        yiq.b = clamp(yiq.b + tint*0.5226*0.1, -0.5226, 0.5226);
        lowp vec3 rgb = YIQtoRGB * yiq;

        lowp vec3 processed = vec3(
                                   (rgb.r < 0.5 ? (2.0 * rgb.r * warmFilter.r) : (1.0 - 2.0 * (1.0 - rgb.r) * (1.0 - warmFilter.r))),
                                   (rgb.g < 0.5 ? (2.0 * rgb.g * warmFilter.g) : (1.0 - 2.0 * (1.0 - rgb.g) * (1.0 - warmFilter.g))),
                                   (rgb.b < 0.5 ? (2.0 * rgb.b * warmFilter.b) : (1.0 - 2.0 * (1.0 - rgb.b) * (1.0 - warmFilter.b))));

        gl_FragColor = vec4(mix(rgb, processed, temperature), source.a);
    }
    else
    {
        vec2 blurCoordinates[24];

    	blurCoordinates[0] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -10.0);
    	blurCoordinates[1] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 10.0);
    	blurCoordinates[2] = textureCoordinate.xy + singleStepOffset * vec2(-10.0, 0.0);
    	blurCoordinates[3] = textureCoordinate.xy + singleStepOffset * vec2(10.0, 0.0);

    	blurCoordinates[4] = textureCoordinate.xy + singleStepOffset * vec2(5.0, -8.0);
    	blurCoordinates[5] = textureCoordinate.xy + singleStepOffset * vec2(5.0, 8.0);
    	blurCoordinates[6] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, 8.0);
    	blurCoordinates[7] = textureCoordinate.xy + singleStepOffset * vec2(-5.0, -8.0);

    	blurCoordinates[8] = textureCoordinate.xy + singleStepOffset * vec2(8.0, -5.0);
    	blurCoordinates[9] = textureCoordinate.xy + singleStepOffset * vec2(8.0, 5.0);
    	blurCoordinates[10] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, 5.0);
    	blurCoordinates[11] = textureCoordinate.xy + singleStepOffset * vec2(-8.0, -5.0);

    	blurCoordinates[12] = textureCoordinate.xy + singleStepOffset * vec2(0.0, -6.0);
    	blurCoordinates[13] = textureCoordinate.xy + singleStepOffset * vec2(0.0, 6.0);
    	blurCoordinates[14] = textureCoordinate.xy + singleStepOffset * vec2(6.0, 0.0);
    	blurCoordinates[15] = textureCoordinate.xy + singleStepOffset * vec2(-6.0, 0.0);

    	blurCoordinates[16] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, -4.0);
    	blurCoordinates[17] = textureCoordinate.xy + singleStepOffset * vec2(-4.0, 4.0);
    	blurCoordinates[18] = textureCoordinate.xy + singleStepOffset * vec2(4.0, -4.0);
    	blurCoordinates[19] = textureCoordinate.xy + singleStepOffset * vec2(4.0, 4.0);

    	blurCoordinates[20] = textureCoordinate.xy + singleStepOffset * vec2(-2.0, -2.0);
    	blurCoordinates[21] = textureCoordinate.xy + singleStepOffset * vec2(-2.0, 2.0);
    	blurCoordinates[22] = textureCoordinate.xy + singleStepOffset * vec2(2.0, -2.0);
    	blurCoordinates[23] = textureCoordinate.xy + singleStepOffset * vec2(2.0, 2.0);


    	float sampleColor = texture2D(inputImageTexture, textureCoordinate).g * 22.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[0]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[1]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[2]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[3]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[4]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[5]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[6]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[7]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[8]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[9]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[10]).g;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[11]).g;

    	sampleColor += texture2D(inputImageTexture, blurCoordinates[12]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[13]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[14]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[15]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[16]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[17]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[18]).g * 2.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[19]).g * 2.0;

    	sampleColor += texture2D(inputImageTexture, blurCoordinates[20]).g * 3.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[21]).g * 3.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[22]).g * 3.0;
    	sampleColor += texture2D(inputImageTexture, blurCoordinates[23]).g * 3.0;

    	sampleColor = sampleColor / 62.0;

    	vec3 centralColor = texture2D(inputImageTexture, textureCoordinate).rgb;

    	float highpass = centralColor.g - sampleColor + 0.5;

    	for(int i = 0; i < 5;i++)
    	{
    		highpass = hardlight(highpass);
    	}
    	float lumance = dot(centralColor, W);

    	float alpha = pow(lumance, params.r);

    	vec3 smoothColor = centralColor + (centralColor-vec3(highpass))*alpha*0.1;

    	smoothColor.r = clamp(pow(smoothColor.r, params.g),0.0,1.0);
    	smoothColor.g = clamp(pow(smoothColor.g, params.g),0.0,1.0);
    	smoothColor.b = clamp(pow(smoothColor.b, params.g),0.0,1.0);

    	vec3 lvse = vec3(1.0)-(vec3(1.0)-smoothColor)*(vec3(1.0)-centralColor);
    	vec3 bianliang = max(smoothColor, centralColor);
    	vec3 rouguang = 2.0*centralColor*smoothColor + centralColor*centralColor - 2.0*centralColor*centralColor*smoothColor;

    	gl_FragColor = vec4(mix(centralColor, lvse, alpha), 1.0);
    	gl_FragColor.rgb = mix(gl_FragColor.rgb, bianliang, alpha);
    	gl_FragColor.rgb = mix(gl_FragColor.rgb, rouguang, params.b);

    	vec3 satcolor = gl_FragColor.rgb * saturateMatrix;
    	gl_FragColor.rgb = mix(gl_FragColor.rgb, satcolor, params.a);
    }
}