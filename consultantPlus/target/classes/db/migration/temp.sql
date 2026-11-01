use consultant;

update admission set province="陕西" where school_id in (2,6);

select count(*) from admission where province = '陕西' and year = (select max(year) from admission where province = '陕西');

select subject, batch, count(*)
from admission
where province = '陕西'
group by subject, batch;