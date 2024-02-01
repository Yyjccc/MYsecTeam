package com.hnust.interflow.Service.AI;

import com.hnust.interflow.Util.Py;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class Wenxin {


	public static void test(String question){
		Py.pyCall("wenxin_common",question);

	}
}
