-- Clear existing data if any
DELETE FROM instances;

-- Insert sample cloud instances
INSERT INTO instances
(instance_type, provider, location, operating_system, vcpu, memory, storage, market_option)
VALUES
('t2.micro', 'AWS', 'us-east-1', 'Linux', 1, 1, 'EBS Only', 'OnDemand'),
('t3.small', 'AWS', 'us-west-2', 'Linux', 2, 2, 'EBS Only', 'Spot'),
('m5.large', 'AWS', 'eu-central-1', 'Windows', 2, 8, '2x300 SSD', 'Reserved'),
('n2-standard-4', 'GCP', 'us-central1', 'Linux', 4, 16, '500GB SSD', 'OnDemand'),
('Standard_D2s_v3', 'Azure', 'eastus', 'Windows', 2, 8, 'Temp Storage', 'PayAsYouGo');