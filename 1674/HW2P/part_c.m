prague_image = imread('prague.jpg');
mall_image = imread('mall.jpg');

energy_image_prague = energy_image(prague_image);
energy_image_mall = energy_image(mall_image);


vert_string = 'VERTICAL';
horz_string = 'HORIZONTAL';

cumulative_map_prague = cumulative_minimum_energy_map(energy_image_prague, horz_string);
seam_prague = find_optimal_horizontal_seam(cumulative_map_prague);
displaySeam(prague_image, seam_prague, horz_string);
saveas(gcf, 'prague_image_seamed.png');

cumulative_map_mall = cumulative_minimum_energy_map(energy_image_mall, vert_string);
seam_mall = find_optimal_vertical_seam(cumulative_map_mall);
displaySeam(mall_image, seam_mall, vert_string);
saveas(gcf, 'mall_image_seamed.png');
