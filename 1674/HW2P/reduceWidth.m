function [reducedColorImage, reducedEnergyImage] = reduceWidth(im, energyImage)

    string = 'VERTICAL';
    cumulative_map = cumulative_minimum_energy_map(energyImage, string);
    h_seam = find_optimal_vertical_seam(cumulative_map);


    [rows, cols, ch] = size(im);
    
    reducedColorImage = zeros(rows, cols - 1, ch, 'uint8');
    reducedEnergyImage = zeros(rows, cols - 1);

    for i=1:rows
        reducedColorImage(i,:,:) = im(i,[1:(h_seam(i)-1) (h_seam(i)+1):end],:);
        reducedEnergyImage(i,:) = energyImage(i,[1:(h_seam(i)-1) (h_seam(i)+1):end]);
    end
end
