function [] = displaySeam(im, seam, seamDirection)
    figure;
    imshow(im);
    
    hold on;
    if strcmp(seamDirection,'HORIZONTAL')
        imshow(im);
        plot(seam);
    
    elseif strcmp(seamDirection,'VERTICAL')
        imshow(im);
        plot(seam, 1:prod(size(seam)));
    end
    hold off;
end