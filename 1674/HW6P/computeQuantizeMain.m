origImg = imread('fish.jpg');

[output_img5, mean_colors5] = quantizeRGB(origImg, 5)
[error5] = computeQuantizationError(origImg,output_img5);

[output_img10, mean_colors10] = quantizeRGB(origImg, 10)
[error10] = computeQuantizationError(origImg,output_img10);

[output_img25, mean_colors25] = quantizeRGB(origImg, 25)
[error25] = computeQuantizationError(origImg,output_img25);

subplot(2,3,1);
imshow(output_img5);
title('RGB quantized, k = 5');
subplot(2,3,3);
imshow(output_img10);
title('RGB quantized, k = 10');
subplot(2,3,5);
imshow(output_img25);
title('RGB quantized, k = 25');