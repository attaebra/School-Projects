function [edges] = detectEdges(im,threshold)
    im = double(rgb2gray(im));
   
    [size_y,size_x] = size(im);
    x = zeros(size_y * size_x, 1);
    y = zeros(size_y * size_x, 1);
    for j = 1:size_x
        for i = 1:size_y
            ind = ((j-1)*size_y)+i;
            x(ind) = j;
            y(ind) = i;
        end
    end
    
    [mag, dir] = imgradient(im);
    mag = mag(:);
    dir = dir(:);
   
    ind = find(mag <= threshold);
    x(ind) = [];
    y(ind) = [];
    mag(ind) = [];
    dir(ind) = [];
   
    edges = [x y mag dir];
end