function [x, y, scores, Ix, Iy] = extract_keypoints(image)
 
    im = double(rgb2gray(image));
    dx = [-1, 0, 1];
    dy = dx';
   
    Ix = double(imfilter(im,dx));
    Iy = double(imfilter(im,dy));
    
    [rows, cols] = size(im);
    offset = 2;
   
    Ix2 = Ix.*Ix;
    Iy2 = Iy.*Iy;
    IxIy = Ix.*Iy;
   
    scores = [];
    x = [];
    y = [];
    k = .04;
    for j = 1+offset:rows-offset
        for i = 1+offset:cols-offset
            M1 = Ix2(j-offset:j+offset,i-offset:i+offset);
            M2 = IxIy(j-offset:j+offset,i-offset:i+offset);
            M3 = Iy2(j-offset:j+offset,i-offset:i+offset);
            M1 = sum(M1(:));
            M2 = sum(M2(:));
            M3 = sum(M3(:));
            M = [M1, M2; M2, M3];
            R = det(M)-(k * (trace(M)).^2);
            x = [x;i];
            y = [y;j];
            scores = [scores; R];
        end
    end
   
    avg = mean(scores);
    threshold =40*avg;
    output = find(scores <= threshold);
    
    x(output) = [];
    y(output) = [];
    scores(output) = [];
end