im1 = imread('prague.jpg');

energyImage = energy_image(im1);
[reducedColorImage, reducedEnergyImage] = reduceHeight(im1, energyImage);

for i = 1:1:99
    [reducedColorImage, reducedEnergyImage] = reduceHeight(reducedColorImage, reducedEnergyImage);
end

figure, imshow(reducedColorImage);
saveas(gcf, 'prague_resize.png');

im2 = imread('mall.jpg');

energyImage2 = energy_image(im2);
[reducedColorImage2 ,reducedEnergyImage2] = reduceWidth(im2,energyImage2);

for i=1:1:99
    [reducedColorImage2 ,reducedEnergyImage2] = reduceWidth(reducedColorImage2, reducedEnergyImage2);
end

figure, imshow(reducedColorImage2);
saveas(gcf, 'mall_resize.png');

[rows1, cols1, chs1] = size(im1);
[rows2, cols2, chs2] = size(im2);

A = imresize(im1, [rows1-100, cols1]);
B = imresize(im2, [rows2, cols2-100]);

figure, imshow(A);
saveas(gcf, 'prague_matlab_resize.png');

figure, imshow(B);
saveas(gcf, 'mall_matlab_resize.png');

