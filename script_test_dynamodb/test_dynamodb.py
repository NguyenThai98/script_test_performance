import boto3
import uuid
import time
from decimal import Decimal  # Import Decimal để thay thế cho float
# Kết nối tới DynamoDB Local
def connect_to_dynamodb():
    # Kết nối với DynamoDB Local, chạy trên localhost:8000
    dynamodb = boto3.resource(
        'dynamodb',
        endpoint_url='http://localhost:8000',  # Địa chỉ và cổng của DynamoDB Local
        region_name='us-west-2',               # Vùng mặc định
        aws_access_key_id='wnjytom',  # Access key giả để tránh yêu cầu
        aws_secret_access_key='lqb6b9'  # Secret key giả để tránh yêu cầu
    )
    return dynamodb

# Tạo bảng cho thử nghiệm
def create_table_dynamodb(dynamodb):
    try:
        table = dynamodb.create_table(
            TableName='Transactions',
            KeySchema=[
                {'AttributeName': 'transaction_id', 'KeyType': 'HASH'}
            ],
            AttributeDefinitions=[
                {'AttributeName': 'transaction_id', 'AttributeType': 'S'}
            ],
            ProvisionedThroughput={
                'ReadCapacityUnits': 10,
                'WriteCapacityUnits': 20
            }
        )
        table.meta.client.get_waiter('table_exists').wait(TableName='Transactions')
        print("DynamoDB - Table created successfully.")
    except dynamodb.meta.client.exceptions.ResourceInUseException:
        print("DynamoDB - Table already exists.")

# Chèn dữ liệu vào bảng
def insert_data_dynamodb(dynamodb, num_records):
    table = dynamodb.Table('Transactions')
    start_time = time.time()
    for _ in range(num_records):
        table.put_item(
            Item={
                'transaction_id': str(uuid.uuid4()),
                'user_id': str(uuid.uuid4()),
                'amount': Decimal('100.0'),
                'transaction_date': str(time.time()),
                'status': 'PENDING'
            }
        )
    duration = time.time() - start_time
    print(f"DynamoDB - Inserted {num_records} records in {duration:.2f} seconds")
    return duration

# Truy vấn dữ liệu từ bảng
def query_data_dynamodb(dynamodb):
    table = dynamodb.Table('Transactions')
    start_time = time.time()
    response = table.scan()
    items = response['Items']
    duration = time.time() - start_time
    print(f"DynamoDB - Queried {len(items)} records in {duration:.2f} seconds")
    return duration

# Main function for DynamoDB
def test_dynamodb():
    dynamodb = connect_to_dynamodb()
    create_table_dynamodb(dynamodb)
    insert_time = insert_data_dynamodb(dynamodb, 1000)  # Thử nghiệm với 1000 bản ghi
    query_time = query_data_dynamodb(dynamodb)
    return insert_time, query_time

insert_time, query_time = test_dynamodb()
print(f"DynamoDB Insert Time: {insert_time:.2f} seconds")
print(f"DynamoDB Query Time: {query_time:.2f} seconds")
