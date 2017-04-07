baby_happy = imread('baby_happy.jpg');
baby_weird = imread('baby_weird.jpg');

baby_happy = imresize(baby_happy, [512, 512]);
baby_weird = imresize(baby_weird, [512, 512]);

%Tried a bunch of different amounts
custom_gaus = fspecial('gaussian', 20, 4);

im1_blur = imfilter(baby_happy, custom_gaus);
figure, imshow(im1_blur);
saveas(gcf, 'im1_blur.jpg')

im2_blur = imfilter(baby_weird, custom_gaus);
figure, imshow(im2_blur);
saveas(gcf, 'im2_blur.jpg')

im2_detail = baby_weird - im2_blur;
figure, imshow(im2_detail);
saveas(gcf, 'im2_detail.jpg');

im_hybrid = im1_blur + im2_detail;
figure, imshow(im_hybrid);
saveas(gcf,'im_hybrid.jpg');