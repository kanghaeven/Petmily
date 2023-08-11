import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useSetRecoilState, useRecoilValue } from 'recoil';
import { string } from 'prop-types';
import authAtom from 'states/auth';
import userAtom from 'states/users';

import { FollowRecommend, Messages, Chat } from 'components';
import SocialFeed from 'components/SocialFeed';
import useFetch from 'utils/fetch';

function Social({ page }) {
  const navigate = useNavigate();
  const auth = useRecoilValue(authAtom);
  const setUser = useSetRecoilState(userAtom);
  const fetchData = useFetch();

  useEffect(() => {
    if (!auth || !Object.keys(auth).length) {
      setUser(null);
      navigate('/login');
    }
    async function checkAuth() {
      try {
        await fetchData.post('authenticate');
      } catch (error) {
        navigate('/login');
      }
    }
    checkAuth();
  }, []);

  return (
    <div className="pb-5 min-w-[1340px] max-w-full w-full absolute top-[6.5rem] flex justify-between font-pretendard">
      <Messages />
      {page === 'feed' ? <SocialFeed /> : null}
      {page === 'chat' ? <Chat /> : null}
      <FollowRecommend />
    </div>
  );
}
Social.propTypes = {
  page: string,
};

export default Social;
