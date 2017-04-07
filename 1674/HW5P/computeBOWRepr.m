function [bow] = computeBOWRepr(descriptors, means)
    [~, vocab_size] = size(means);
    means = means';
    
    distance_matrix = dist2(descriptors, means);
    bow = zeros(vocab_size, 1);
    
    for i = (1:size(distance_matrix,1))
        mindex = find(distance_matrix(i,:)==min(distance_matrix(i,:)));
        bow(mindex) = bow(mindex) + 1;
    end
end