%Kendrick Lamar Horizontal
kenny_image = imread('cornrow_kenny.png');
[rows, cols, ch] = size(kenny_image)

energyImage = energy_image(kenny_image);
[reducedColorImageH, reducedEnergyImageH] = reduceHeight(kenny_image, energyImage);

for i=1:1:450
    [reducedColorImageH, reducedEnergyImageH] = reduceHeight(reducedColorImageH, reducedEnergyImageH);
end

figure, imshow(reducedColorImageH);
saveas(gcf, 'cornrow_kenny_heightadj.png');

C = imresize(kenny_image, [rows-450, cols]);

figure, imshow(C);
saveas(gcf, 'kennyH_matlab_resize.png');

%Kendrick Lamar Vertical
[reducedColorImageV ,reducedEnergyImageV] = reduceWidth(kenny_image,energyImage);

for i=1:1:450
    [reducedColorImageV ,reducedEnergyImageV] = reduceWidth(reducedColorImageV, reducedEnergyImageV);
end

figure, imshow(reducedColorImageV);
saveas(gcf, 'corn_row_kenny_widthadj.png');

D = imresize(kenny_image, [rows, cols-450]);

figure, imshow(D);
saveas(gcf, 'kennyV_matlab_resize.png');

