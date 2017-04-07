function [outputImg, meanColors] = quantizeRGB(origImg, k)
    [m,n,~] = size(origImg);
    num_pixels = m * n;
    origImg = im2double(origImg);
    X = reshape(origImg, num_pixels, 3);

    [Idx,C] = kmeans(X,k);

        for i = 1:num_pixels
            X(i,:) = C(Idx(i,1),:);
        end

    outputImg = reshape(X,m,n,3);
    outputImg = round((outputImg * 255), 0);
    outputImg = uint8(outputImg);
    meanColors = C;
end