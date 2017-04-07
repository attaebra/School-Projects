prague_image = imread('prague.jpg');
mall_image = imread('mall.jpg');

energy_image_prague = energy_image(prague_image);
energy_image_mall = energy_image(mall_image);

figure, imshow(energy_image_prague);
saveas(gcf, 'energy_prague.png');

figure, imshow(energy_image_mall);
saveas(gcf, 'energy_mall.png');

hor_string = 'HORIZONTAL';
vert_string = 'VERTICAL';

cumul_min_map_prague_horiz = cumulative_minimum_energy_map(energy_image_prague, hor_string);
cumul_min_map_mall_horiz = cumulative_minimum_energy_map(energy_image_mall, hor_string);

cumul_min_map_prague_vert = cumulative_minimum_energy_map(energy_image_prague, vert_string);
cumul_min_map_mall_vert = cumulative_minimum_energy_map(energy_image_mall, vert_string);

figure, imagesc(cumul_min_map_prague_horiz);
saveas(gcf, 'cumul_min_map_prague_horiz.png');

figure, imagesc(cumul_min_map_prague_vert);
saveas(gcf, 'cumul_min_map_prague_vert.png');

figure, imagesc(cumul_min_map_mall_horiz);
saveas(gcf, 'cumul_min_map_mall_horiz.png');

figure, imagesc(cumul_min_map_mall_vert);
saveas(gcf, 'cumul_min_map_mall_vert.png');