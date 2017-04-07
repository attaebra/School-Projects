forest = imread('forest.jpg');
forest_gray = rgb2gray(forest);

filt_gaus = fspecial('gaussian', 9, 3);
[gaussian_pyramid, laplacian_pyramid] = pyramids(forest_gray, filt_gaus);


for i = 1:5
    subplottight(1,5,i)
    imshow(gaussian_pyramid{i}, 'border', 'tight');
end
saveas(gcf, 'gaussian_pyramid_forest.jpg');

figure
 for i = 1:5
    subplottight(1,5,i);
    imshow(laplacian_pyramid{i}, 'border', 'tight');
 end
 saveas(gcf, 'laplacian_pyramid_forest.jpg');