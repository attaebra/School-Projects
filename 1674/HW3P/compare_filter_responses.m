clear; clc;
F = makeLMfilters();

%Parts 1-4
name_array = {'cardinal1.jpg','cardinal2.jpg', 'leopard1.jpg', 'leopard2.jpg', 'panda1.jpg', 'panda2.jpg'};
save_names = {'cardinal1_responses', 'cardinal2_responses', 'leopard1_responses', 'leopard2_responses', 'panda1_responses', 'panda2_responses',};
histograms = [];

for i = 1:size(name_array,2)
    image = imread(name_array{i});
    image = imresize(image, [512 512]);
    image = imresize(image, .5);
    image = rgb2gray(image);

    
    figure;
    histogram = [];
    for j = 1:48
        filt_image(:,:,j) = imfilter(image, F(:, :, j));
        filt_im = filt_image(:);
        
        subplottight(8,6,j);
        imshow(filt_image(:,:,j), 'border', 'tight');
        bincounts = histc(filt_im, 2.^(0:0.5:7));
        histogram = [histogram ; bincounts];
    end
    saveas(gcf, save_names{i});
    histograms(:,size(histograms,2)+1) = histogram;
end

%Part 5
within = [];
between = [];


temp = [];

%This is the values for both between and within
for k = 1:6
    for i = 1:5
        clear temp hold;
        temp = [];
        for j =1:720
            hold = sqrt(sum((histograms(j,k)-histograms(j, i+1)).^2));
            temp = [temp; hold];
        end
        within(:,size(within,2)+1) = temp;
    end
end



