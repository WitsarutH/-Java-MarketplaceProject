# cs211-641-project

## รายชื่อสมาชิก
* 6310401165 วิศรุต หอมแก่นจันทร์ luckynakabku
  - ระบบเข้าสู่ระบบ(Login)
  - ตั้งค่าบัญชี (เปลี่ยนรูปโปรไฟล์,เปลี่ยนรหัสผ่าน)
  - ระบบจัดเรียงสินค้าตามราคา
  - ระบบคำนวณเงิน
  - เมนูสำหรับผู้ดูแลระบบ
  - ประวัติการซื้อ-ขาย
* 6310401106 ภูมิรัฐ เลิบไสง bhumirath 
  - ระบบแสดงรายการสินค้า(User)
  - ระบบแสดงรายการสินค้าของร้าน(Seller)
  - เมนูซื้อสินค้า
  - เมนู Welcome Homepage
* 6310401998 ศิวกร แพทย์เสลา siwakornpatsalao
  - ระบบสมัครสมาชิก
  - เมนูเปิดร้านค้า
  - เมนูตั้งค่าการแจ้งเตือนเมื่อสินค้าในคลังมีจำนวนน้อย



## วิธีการติดตั้งหรือรันโปรแกรม
การเรียกใช้งานโปรแกรม LuckyPureLevi Shop 
1. จะเรียกใช้งานผ่าน Directory  \LuckyPureLeviShop\bin 
2. หลังจากนั้นให้ใช้คำสั่ง Git Bash Here ในการเรียกใช้งาน Command Prompt
3. สำหรับคำสั่งในการเปิดใช้งานโปรแกรมภายใน Command Prompt คือ ./launch.sh


## การวางโครงสร้างไฟล์
โฟลเดอร์ data
- เก็บไฟล์รูปภาพ
- เก็บไฟล์ข้อมูล csv

โฟลเดอร์ src
- เก็บ Source code ทั้งหมดของโปรแกรม
- เก็บรูปภาพเริ่มต้นของโปรแกรม

โฟลเดอร์ LuckyPureLeviShop
- เก็บโปรแกรมที่ทำการ Compile เสร็จแล้ว และพร้อมใช้งานแล้ว

## ตัวอย่างข้อมูลผู้ใช้ระบบ
* (Role) (username) (password)
* admin    admin       admin
* seller   adam        13579
* seller   tony        456789
* seller   lucky       1234
* seller  paopao       ihave

## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ
* ครั้งที่ 1 (11/8/2564)
  * Home page        พัฒนาโดย ภูมิรัฐ เลิบไสง bhumirath
  * Developer page     พัฒนาโดย ศิวกร แพทย์เสลา siwakornpatsalao
  * Register page    พัฒนาโดย วิศรุต หอมแก่นจันทร์ luckynakabku
  * Login page       พัฒนาโดย วิศรุต หอมแก่นจันทร์ luckynakabku
* ครั้งที่ 2 (6/9/2564)
  * Login Controller พัฒนาโดย วิศรุต หอมแก่นจันทร์ luckynakabku
  * Register Controller พัฒนาโดย ศิวกร แพทย์เสลา siwakornpatsalao
  * Home Controller พัฒนาโดย ภูมิรัฐ เลิบไสง bhumirath
* ครั้งที่ 3 (27/9/2564)
  * profile page, profile controller พัฒนาโดย วิศรุต หอมแก่นจันทร์ luckynakabku
  * admin page พัฒนาโดย วิศรุต หอมแก่นจันทร์ luckynakabku
  * marketplace พัฒนาโดย ภูมิรัฐ เลิบไสง bhumirath
  * product detail พัฒนาโดย ภูมิรัฐ เลิบไสง bhumirath
  * shop controller พัฒนาโดย ศิวกร แพทย์เสลา siwakornpatsalao
