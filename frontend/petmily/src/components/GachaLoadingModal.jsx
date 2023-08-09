import { useEffect } from 'react';
import { func, string } from 'prop-types';
import axios from 'axios';
import { useRecoilValue } from 'recoil';

import { Player } from '@lottiefiles/react-lottie-player';
import gachaLoading from 'static/animations/gachaLoading.json';
import userAtom from 'states/users';

function GachaLoadingModal({ onClose, gachaOpen, gachaSelect, setGachaItem }) {
  const user = useRecoilValue(userAtom);
  useEffect(() => {
    // 서버에 뽑기 요청
    console.log('뽑기 요청');
    async function fetchData() {
      try {
        const response = await axios.post('item/getRandom', {
          userEmail: user.userEmail,
          randomKind: gachaSelect,
        });
        onClose();
        console.log(response);
        setGachaItem(response.data);
        gachaOpen();
      } catch (error) {
        console.error(error);
      }
    }
    fetchData();
  }, [gachaOpen, gachaSelect, onClose, setGachaItem, user.userEmail]);

  return (
    <div className="relative rounded-[10px] bg-inherit w-[656px] h-[450px] max-w-full flex flex-col justify-center items-center max-h-full text-left text-xl text-darkgray font-pretendard">
      <Player loop src={gachaLoading} autoplay />
    </div>
  );
}

GachaLoadingModal.propTypes = {
  onClose: func,
  gachaOpen: func,
  gachaSelect: string,
  setGachaItem: func,
};

export default GachaLoadingModal;