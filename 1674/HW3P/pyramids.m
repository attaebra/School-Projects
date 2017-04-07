function [G, L] = pyramids(im, fil)
    
    G = {1,5};
    L = {1,5};
    
    for i = 1:5
        if i == 1
            im_blur = imfilter(im, fil);
            im_laplace = im - im_blur;
            G{i} =  im;
            L{i} = im_laplace;
        else
            im = im(1:2:end, 1:2:end);
            im_blur = imfilter(im, fil);
            im_laplace = im - im_blur;
            G{i} = im;
            L{i} = im_laplace;
        end
    end
end