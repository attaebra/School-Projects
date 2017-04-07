function [features, x, y, scores] = compute_features(x, y, scores, Ix, Iy)
    var = 6;
    row = size(x,2);
    for i = 1:1:row
        if i > size(x,2)
            break
        end
        if ( (x(i) <= var) || (y(i) <= var) || ...
                (x(i) >= size(Ix,2)-var) || (y(i) >= size(Iy,1)-var) )
            x(i) = [];
            y(i) = [];
            scores(i) = [];
            row = row-1;
        end
    end
    
    off = 5;
    features = [];
    for point = 1:1:size(x,2)
        hist = zeros(1,8); 
    end    
end
    
    