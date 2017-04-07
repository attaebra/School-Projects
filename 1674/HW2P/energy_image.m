function return_value = energy_image(im)
    image_gray = rgb2gray(im);
    x_diff = double(imfilter(image_gray, [-1,0,1; -1, 0, -1; -1, 0, -1]));
    y_diff = double(imfilter(image_gray, [-1, -1, -1; 0, 0, 0; 1, 1, 1]));
    return_value = sqrt(x_diff .^2 + y_diff .^ 2);
end