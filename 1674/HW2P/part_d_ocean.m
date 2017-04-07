%Ocean Horizontal
ocean_image = imread('ocean.jpg');
[rows, cols, ch] = size(ocean_image)

energyImage = energy_image(ocean_image);
[reducedColorImageH, reducedEnergyImageH] = reduceHeight(ocean_image, energyImage);

for i=1:1:199
    [reducedColorImageH, reducedEnergyImageH] = reduceHeight(reducedColorImageH, reducedEnergyImageH);
end

figure, imshow(reducedColorImageH);
saveas(gcf, 'ocean_heightadj.png');

C = imresize(ocean_image, [rows-199, cols]);

figure, imshow(C);
saveas(gcf, 'oceanH_matlab_resize.png');

%ocean Vertical
[reducedColorImageV ,reducedEnergyImageV] = reduceWidth(ocean_image,energyImage);

for i=1:1:199
    [reducedColorImageV ,reducedEnergyImageV] = reduceWidth(reducedColorImageV, reducedEnergyImageV);
end

figure, imshow(reducedColorImageV);
saveas(gcf, 'ocean_widthadj.png');

D = imresize(ocean_image, [rows, cols-199]);

figure, imshow(D);
saveas(gcf, 'ocean_resize.png');