clear, clc;

imageset = {'brick.jpg', 'chess.jpg', 'eagle.jpg', 'flag.jpg', 'nyc.jpg', 'snake.jpg', 'soccer.jpg', 'warehouse.jpg', 'iphone.jpg', 'android.jpg'};
imagesave = {'brick_out.jpg', 'chess_out.jpg', 'eagle_out.jpg', 'flag_out.jpg', 'nyc_out.jpg', 'snake_out.jpg', 'soccer_out.jpg', 'warehouse_out.jpg', 'iphone_out.jpg', 'android_out.jpg'};
    

for i = 1:size(imageset,2)
    image = imread(imageset{i});
    image = imresize(image, .25);
    [x, y, scores, Ix, Iy] = extract_keypoints(image);

    figure, imshow(image);
    hold on;

    for j = 1:size(y)
        plot(x(j), y(j), 'ro', 'MarkerSize', 5);
    end
    saveas(gcf, imagesave{i});
    %[features, x, y, scores] = compute_features(x, y, scores, Ix, Iy);
end
