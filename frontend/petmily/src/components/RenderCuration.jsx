import { memo, useCallback, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';

import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import { styled } from '@mui/material';
import { arrayOf, bool, shape, string } from 'prop-types';
import { useRecoilState } from 'recoil';
import { v4 as uuidv4 } from 'uuid';
import userAtom from 'states/users';
import useFetch from 'utils/fetch';
import { icons } from 'utils/utils';

function RenderCuration({ category, showMore = true, renderData }) {
  console.log('reCuration', renderData);
  const memoizedRenderData = useMemo(() => renderData, [renderData]);
  const navigation = useNavigate();
  const [userInfo, setUserInfo] = useRecoilState(userAtom);
  const fetchData = useFetch();

  const StyledArrowForwardIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({});
  const path = decodeURIComponent(window.location.pathname);
  const handleShowMoreClick = clickedCategory => {
    if (path.includes('pet')) {
      navigation('/category', {
        state: { petType: path.split('/').at(-1), category: clickedCategory },
      });
    } else {
      navigation('/pet', {
        state: { petType: clickedCategory },
      });
    }
  };
  const handleBookmark = useCallback(
    curationId => {
      if (userInfo.userToken === null) {
        navigation('/login');
      }
      try {
        const data = fetchData.put('/curation/bookmark', {
          userEmail: userInfo.userEmail,
          cid: curationId,
        });
        console.log(data);
        setUserInfo({ ...userInfo, bookmarks: data });
      } catch (error) {
        console.log(error);
      }
    },
    [fetchData, setUserInfo, userInfo],
  );

  return (
    <div className="min-w-[1340px] max-w-full flex flex-col items-start justify-start gap-[2.25rem] mb-5 mt-5">
      <div className="self-stretch flex flex-row items-center justify-between">
        <div
          className={`rounded-31xl ${
            category === '인기'
              ? ' bg-white text-dodgerblue border-solid border-2 border-dodgerblue'
              : 'bg-dodgerblue'
          } overflow-hidden flex flex-row py-2.5 px-5 items-center justify-start gap-[0.5rem]`}
        >
          <img
            src={icons[category]}
            alt=""
            className="relative w-[38px] h-auto"
          />
          <div className="relative tracking-[0.01em] leading-[125%] font-extrabold px-2 rounded-xl text-xl">
            {category}
          </div>
        </div>
        {showMore && category !== '인기' ? (
          <div
            className="rounded-31xl overflow-hidden flex flex-row py-2.5 px-5 items-center bg-white gap-[12px] text-[1.25rem] text-dodgerblue border-[2px] border-solid cursor-pointer"
            role="presentation"
            onClick={() => {
              handleShowMoreClick(category);
            }}
          >
            <b className="relative tracking-[0.01em] leading-[125%] text-dodgerblue">
              더보기
            </b>
            <StyledArrowForwardIosRoundedIcon className="text-dodgerblue" />
          </div>
        ) : null}
      </div>
      <div className="min-w-[1340px] flex-wrap max-w-full flex flex-row items-start justify-start gap-[24.96px] text-[1rem] text-gray">
        {showMore
          ? memoizedRenderData?.slice(0, 5).map(ele => {
              return (
                <div
                  key={ele.cid}
                  className="relative flex-1 min-w-[250px] rounded-11xl bg-white overflow-hidden flex flex-col pt-0 pb-6 items-center justify-center gap-[16px]"
                >
                  {/* 유저 북마크 정보랑 비교해서 큐레이션 카드 ID가 북마크 정보 리스트안에 있으면
               TRUE 아니면 FALSE */}
                  {!userInfo.bookmarks?.includes(ele.cid) ? (
                    <BookmarkBorderIcon
                      className="absolute bottom-2 right-3 cursor-pointer z-10"
                      color="primary"
                      onClick={() => {
                        handleBookmark(ele.cid);
                      }}
                      fontSize="large"
                    >
                      북마크
                    </BookmarkBorderIcon>
                  ) : (
                    <BookmarkIcon
                      className="relative top-5 left-36 cursor-pointer z-10"
                      color="primary"
                      onClick={() => {
                        handleBookmark(ele.cid);
                      }}
                    />
                  )}
                  <a
                    href={ele.curl}
                    className="w-fit h-fit no-underline text-black"
                    target="_blank"
                    rel="noreferrer"
                  >
                    <img
                      className="relative w-full object-fill"
                      alt=""
                      src={ele.cimage}
                    />
                    <div className="flex flex-col items-start justify-center gap-[16px] w-fit py-2">
                      <div className="flex flex-row items-center justify-center gap-[12px]">
                        <div className="relative tracking-[0.01em] leading-[125%] font-semibold text-[1.3rem] flex items-start px-3">
                          {ele.ctitle}
                        </div>
                      </div>
                      <div className="relative text-[0.9rem] tracking-[0.01em] leading-[125%] text-darkgray flex items-center px-4">
                        {ele.ccontent}
                      </div>
                    </div>
                  </a>
                </div>
              );
            })
          : renderData?.map(ele => {
              return (
                <div
                  key={uuidv4()}
                  className="relative flex-1 max-w-[350px] min-w-[250px] rounded-11xl bg-white overflow-hidden flex flex-col pt-0 px-0 pb-6 items-center justify-center gap-[16px]"
                >
                  {/* 유저 북마크 정보랑 비교해서 큐레이션 카드 ID가 북마크 정보 리스트안에 있으면
               TRUE 아니면 FALSE */}
                  {!userInfo.bookmarks?.includes(ele.cid) ? (
                    <BookmarkBorderIcon
                      className="absolute bottom-3 right-3 cursor-pointer z-10"
                      color="primary"
                      onClick={() => {
                        handleBookmark(ele.cid);
                      }}
                    >
                      북마크
                    </BookmarkBorderIcon>
                  ) : (
                    <BookmarkIcon
                      className="relative top-5 left-36 cursor-pointer z-10"
                      color="primary"
                      onClick={() => {
                        handleBookmark(ele.cid);
                      }}
                    />
                  )}
                  <a
                    href={ele.curl}
                    className="w-fit h-fit no-underline text-black"
                    target="_blank"
                    rel="noreferrer"
                  >
                    <img
                      className="relative w-full object-fill"
                      alt=""
                      src={ele.cimage}
                    />
                    <div className="flex flex-col items-start justify-center gap-[16px] p-4 w-fit">
                      <div className="flex flex-row items-center justify-center gap-[12px]">
                        <div className="relative tracking-[0.01em] leading-[125%] font-medium flex items-start">
                          {ele.ctitle}
                        </div>
                      </div>
                      <div className="relative text-[0.88rem] tracking-[0.01em] leading-[125%] text-darkgray flex items-center">
                        {ele.ccontent}
                      </div>
                    </div>
                  </a>
                </div>
              );
            })}
      </div>
    </div>
  );
}

const rederDataType = shape({
  ctitle: string,
  ccontent: string,
  cimage: string,
  cdate: string,
  curl: string,
  cpetSpecies: string,
  ccategory: string,
});

RenderCuration.propTypes = {
  category: string,
  showMore: bool,
  renderData: arrayOf(rederDataType),
};
export default memo(RenderCuration);
