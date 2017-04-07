%Penguin Horizontal
penguin_image = imread('penguin.jpg');
[rows, cols, ch] = size(penguin_image)

energyImage = energy_image(penguin_image);
[reducedColorImageH, reducedEnergyImageH] = reduceHeight(penguin_image, energyImage);

for i=1:1:199
    [reducedColorImageH, reducedEnergyImageH] = reduceHeight(reducedColorImageH, reducedEnergyImageH);
end

figure, imshow(reducedColorImageH);
saveas(gcf, 'penguin_heightadj.png');

C = imresize(penguin_image, [rows-199, cols]);

figure, imshow(C);
saveas(gcf, 'penguinH_matlab_resize.png');

%Penguin Vertical
[reducedColorImageV ,reducedEnergyImageV] = reduceWidth(penguin_image,energyImage);

for i=1:1:199
    [reducedColorImageV ,reducedEnergyImageV] = reduceWidth(reducedColorImageV, reducedEnergyImageV);
end

figure, imshow(reducedColorImageV);
saveas(gcf, 'penguin_widthadj.png');

D = imresize(penguin_image, [rows, cols-199]);

figure, imshow(D);
saveas(gcf, 'penguin_resize.png');