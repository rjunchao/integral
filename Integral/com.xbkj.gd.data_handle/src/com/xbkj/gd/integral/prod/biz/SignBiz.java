/**
 * 
 */
package com.xbkj.gd.integral.prod.biz;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.annotation.Bizlet;
import com.eos.system.logging.Logger;
import com.pub.xbkj.common.MsgResponse;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.common.util.StringUtil;
import com.xbkj.gd.integral.prod.common.IntegralConstant;
import com.xbkj.gd.integral.prod.common.SignUtils;
import com.xbkj.gd.integral.prod.dao.SignDao;
import com.xbkj.gd.integral.prod.vos.SignVO;
import com.xbkj.gd.utils.ConfigUtils;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-27
 *@version 1.0.0
 *@desc 保存签名和查询看签名
 */

@Bizlet("")
public class SignBiz {
	private static final Logger log = LoggerFactory.getLogger(SignBiz.class);
	
	private SignDao dao = new SignDao();
//	private IntegralSignDao isDao = new IntegralSignDao();

	@Bizlet()
	public MsgResponse findSign(Map<String, String> params) throws IOException{
		MsgResponse msg = new MsgResponse(false);
		String pk = params.get("pk");
		String sign = SignUtils.getSign(pk);
		if(StringUtil.isNotEmpty(sign)){
//			byte[] signByte = Base64.getDecoder().decode(sign.getBytes());
			msg.setObj(IntegralConstant.IMG_PRFIX + sign);
			msg.setFlag(true);
			return msg;
		}
		msg.setMessage("签名不存在");
		return msg;
	}
	
	@Bizlet()
	public MsgResponse saveSign(Map<String, String> params){
		MsgResponse msg = new MsgResponse(false);
		String sign = params.get("sign");
		if(StringUtil.isEmpty(sign)){
			msg.setMessage("请签名");
			return msg;
		}
		String signPath = ConfigUtils.get("cust.sign.path");
		SignVO vo = new SignVO();
		String pk = PrimaryKeyUtil.getPrimaryKey();
		vo.setPk_sign(pk);
		String fileName =  pk + ".txt";
		vo.setSign_file_name(fileName);//
		File signFile = new File(signPath + fileName);
//		FileOutputStream fos = null;
		try {
			FileUtils.writeByteArrayToFile(signFile, sign.getBytes());
			/*fos = new FileOutputStream(signFile);
			fos.write(sign.getBytes());
			fos.flush();*/
		} catch (IOException e) {
			log.error(e);
			msg.setMessage("保存签名失败" + e.getMessage());
			return msg;
		}/*finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {}
			}
		}*/
		vo.setSign_file_path(signPath);
		dao.save(vo);
		msg.setFlag(true);
		msg.setObj(pk);//签名主键
		msg.setMessage("签名保存成功");
		return msg;
	}
/*	@Bizlet()
	public MsgResponse saveSign(Map<String, String> params){
		MsgResponse msg = new MsgResponse(false);
		String ids = params.get("ids");
		if(StringUtil.isEmpty(ids)){
			msg.setMessage("请选择");
			return msg;
		}
		String sign = params.get("sign");
		if(StringUtil.isEmpty(sign)){
			msg.setMessage("请签名");
			return msg;
		}
		String signPath = ConfigUtils.get("cust.sign.path");
		SignVO vo = new SignVO();
		String pk = PrimaryKeyUtil.getPrimaryKey();
		vo.setPk_sign(pk);
		String fileName =  pk + ".txt";
		vo.setSign_file_name(fileName);//
		File signFile = new File(signPath + fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(signFile);
			fos.write(sign.getBytes());
			fos.flush();
		} catch (IOException e) {
			log.error(e);
			msg.setMessage("保存签名失败" + e.getMessage());
			return msg;
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {}
			}
		}
		vo.setSign_file_path(signPath);
		dao.save(vo);
		String[] split = ids.split(",");
		if(split != null && split.length > 0){
//			IntegralSignVO is = new IntegralSignVO();
			SQLParameter parameter = null;
			for(String id : split){
				if(StringUtil.isNotEmpty(id)){
					//					is = new IntegralSignVO();
//					is.setPk_integral_detail(id);
//					is.setPk_sign(pk);
//					is.setPk_integral_sign(PrimaryKeyUtil.getPrimaryKey());
//					isDao.save(is);
					//更新签名记录
					parameter = new SQLParameter();
					parameter.addParam(pk);//签名主键
					parameter.addParam(id);//明细主键
					isDao.updateDetailSign(parameter);
					CacheSessionFactory.getInstance().put(id, sign);//缓存数据
				}
			}
		}
		msg.setFlag(true);
		msg.setMessage("签名保存成功");
		return msg;
	}
*/
	@Bizlet("")
	public String signQueryTest(){
		// 1
		/*BASE64Encoder be = new BASE64Encoder();
		ByteOutputStream bos = new ByteOutputStream();
		FileInputStream is = null;
		try {
//			File f = new File("f:\\Decode_Image_29280.txt");
			is = new FileInputStream("f:\\Decode_Image_29280.PNG");
			byte[] buf = new byte[1024];
			while(is.read(buf) != -1){
				bos.write(buf);
			}
			String encode = be.encode(bos.getBytes());
			bos.close();
			return "data:image/png;base64," + encode;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;*/
		
		// 2
		FileInputStream is = null;
		try {
			File f = new File("F:\\MyProject\\Integral\\path\\GD1126352701731368960.txt");
			is = new FileInputStream(f);
			int len = is.available();
			byte[] buf = new byte[len];
			is.read(buf);
			String str = new String(buf);
			str = str.replaceAll("[\\s*\t\n\r]", "");
			return "data:image/jpg;base64," + str;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		return null;
	}
}
