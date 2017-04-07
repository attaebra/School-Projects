clc, clear, close all;

image1 = imread('img1.png');
image2 = imread('img2.png');

% figure, imshow(image1), impixelinfo;
% figure, imshow(image2), impixelinfo;

A = [157, 103; 253, 103; 226, 128; 337, 15];
B = [59, 116; 188, 116; 169, 144; 242, 35];

H = compute_homography(A, B);

testPoint = [170 116]
p2 = apply_homography(testPoint, H);

figure, imshow(image1), hold on, plot(209, 155, 'yo');
saveas(gcf,'part2_yellow.png');
figure, imshow(image2), hold on, plot(p2(1), p2(2), 'ro');
saveas(gcf,'part2_red.png');