function [reducedColorImage, reducedEnergyImage] = reduceHeight(im, energyImage)

    string = 'HORIZONTAL';
    cumulative_map = cumulative_minimum_energy_map(energyImage, string);
    seam = find_optimal_horizontal_seam(cumulative_map);

    [rows, cols, ch] = size(im);
    
    reducedColorImage = zeros(rows - 1, cols, ch, 'uint8');
    reducedEnergyImage = zeros(rows - 1, cols);
    

    for i=1:cols
        reducedColorImage(:,i,:) = im([1:(seam(i)-1) (seam(i)+1):end],i,:);
        reducedEnergyImage(:,i) = energyImage([1:(seam(i)-1) (seam(i)+1):end],i);
    end
end