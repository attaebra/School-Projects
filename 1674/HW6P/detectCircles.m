function [centers] = detectCircles(im, edges, radius, top_k)
    edge_count = size(edges, 1);
    quant_value = 2;

    [height, width, ~] = size(im);

    h = zeros(height, width);
    
    for i = (1:edge_count)
        row = edges(i, 1);
        col = edges(i, 2);
        degree = edges(i, 4);

        a = abs(ceil(col - radius * cos(degree))) + 1;
        b = abs(ceil(row - radius * sin(degree))) + 1;

        a = ceil(a / quant_value);
        b = ceil(b / quant_value);

        h(a, b) = h(a, b) + 1;
    end
 
    [~, sort_index] = sort(h(:), 'descend');
    max_index = sort_index(1:top_k);
    
    centers = [];
    [r, c] = ind2sub(size(h), max_index);

    for i = (1:top_k)
        centers(i, 1) = r(i) * quant_value;
        centers(i, 2) = c(i) * quant_value;
    end

    figure;
    imshow(im);
    viscircles(centers, radius * ones(size(centers, 1), 1));
    saveas(gcf, 'output.png');
end