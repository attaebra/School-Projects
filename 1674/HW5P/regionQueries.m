clear, clc, close all;

examples = 6;
query_size = 3;
image_save = {'ex1.jpg', 'ex2.jpg', 'ex3.jpg', 'ex3.jpg', 'ex4.jpg', 'ex5.jpg'};
image_out = {'ex1_out.jpg', 'ex2_out.jpg', 'ex3_out.jpg', 'ex3_out.jpg', 'ex4_out.jpg', 'ex5_out.jpg'};


siftdir = ('C:\Users\atta_\Google Drive\CS\1674\HW5P\sift_subset\');

fnames = dir([siftdir '*.mat']);
frame_count = length(fnames);

siftdir = ('C:\Users\atta_\Google Drive\CS\1674\HW5P\sift_subset\');
framesdir = ('C:\Users\atta_\Google Drive\CS\1674\HW5P\frames_subset\');

fnames = dir([siftdir '*.mat']);
frame_count = length(fnames);
random_frame_indecies = randperm(frame_count);

bows = [];

% Load saved vocabular file
load('centers.mat', 'membership', 'means');


img_names = [];

    for i = (1:frame_count)
        fname = [siftdir '/' fnames(i).name];
        load(fname, 'imname', 'descriptors', 'positions', 'scales', 'orients');
        img_names = [img_names; imname];

        bow = computeBOWRepr(descriptors, means);
        bows(i, :) = bow;
    end

for example = (1:examples)
    results = []

    query_name = [siftdir '/' fnames(random_frame_indecies(example)).name];
    load(query_name, 'imname', 'descriptors', 'positions', 'scales', 'orients');

    query_img = imread([framesdir '/' imname]);

    figure;
    oninds = selectRegion(query_img, positions);
    patch_sifts = descriptors(oninds, :);
    
    saveas(gcf, image_save{example});

    query_bow = computeBOWRepr(patch_sifts, means);

    for frame = (1:frame_count)
        frame_bow = bows(frame, :);
        dist_check = compareSimilarity(query_bow, frame_bow);

        if dist_check < 1
            results = [results; frame, dist_check];
        end
    end

    results = sortrows(results, -2);

    figure;
    for similar_image = (1:query_size)
        sim_index = results(similar_image, 1);
        sim_img = imread([framesdir '/' img_names(sim_index, :)]); 
        subplot(1, query_size, similar_image), imshow(sim_img);
    end
    saveas(gcf, image_out{example});
end